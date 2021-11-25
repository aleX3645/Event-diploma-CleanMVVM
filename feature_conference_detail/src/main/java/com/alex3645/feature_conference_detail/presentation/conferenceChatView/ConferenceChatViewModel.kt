package com.alex3645.feature_conference_detail.presentation.conferenceChatView

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alex3645.app.android.SharedPreferencesManager
import com.alex3645.base.presentation.BaseAction
import com.alex3645.base.presentation.BaseAndroidViewModel
import com.alex3645.base.presentation.BaseViewState
import com.alex3645.feature_conference_detail.data.model.ChatMessage
import com.alex3645.feature_conference_detail.data.model.UserMessage
import com.alex3645.feature_conference_detail.di.component.DaggerConferenceDetailViewModelComponent
import com.alex3645.feature_conference_detail.usecase.ConnectToChatUseCase
import com.alex3645.feature_conference_detail.usecase.LoadAccountByLoginUseCase
import com.alex3645.feature_conference_detail.usecase.LoadChatUseCase
import com.alex3645.feature_conference_detail.usecase.SendMessageUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ConferenceChatViewModel : BaseAndroidViewModel<ConferenceChatViewModel.ViewState, ConferenceChatViewModel.Action> {

    constructor(application: Application) : super(ViewState(),application) {
        val spManager = SharedPreferencesManager(application)
        username = spManager.fetchLogin()?:""
    }

    init {
        DaggerConferenceDetailViewModelComponent.factory().create().inject(this)
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val errorMessage: String = ""
    ) : BaseViewState

    interface Action : BaseAction {
        class Failure(val message: String) : Action
    }

    @Inject
    lateinit var connectToChatUseCase: ConnectToChatUseCase
    @Inject
    lateinit var loadChatUseCase: LoadChatUseCase
    @Inject
    lateinit var sendMessageUseCase: SendMessageUseCase
    @Inject
    lateinit var loadAccountByLoginUseCase: LoadAccountByLoginUseCase

    var conferenceId: Long = 0
    var username: String = ""
    private val simpleDateFormatServer = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
    fun sendMessage(message: String){
        viewModelScope.launch {
            loadAccountByLoginUseCase(username).also { result ->
                val action = when (result) {
                    is LoadAccountByLoginUseCase.Result.Success ->{
                        Log.d("!!!",username)
                        sendMessageFromUser(result.user.toChatMessageUser(), message)
                        return@also
                    }
                    is LoadAccountByLoginUseCase.Result.Error ->
                        Action.Failure(result.e.message ?: "Ошибка подключения")
                    else -> Action.Failure("Ошибка подключения")
                }
                sendAction(action)
            }
        }
    }

    private fun sendMessageFromUser(user: UserMessage,message: String){
        viewModelScope.launch {
            sendMessageUseCase(
                ChatMessage(
                    id = 0,
                    conferenceId = conferenceId,
                    user = user,
                    content = message,
                    dateTime = simpleDateFormatServer.format(Calendar.getInstance(Locale.getDefault()).time)
                )
            ).also{ result ->
                    val action = when (result) {
                        is SendMessageUseCase.Result.Success -> {
                            //loadMessages()
                            return@also
                        }
                        is SendMessageUseCase.Result.Error ->
                            Action.Failure(result.e.message?: "Ошибка")
                        else -> Action.Failure("Ошибка подключения")
                    }
                    sendAction(action)
                }
        }
    }



    val liveDataMessages: MutableLiveData<MutableList<ChatMessage>> by lazy {
        MutableLiveData()
    }

    private var messages: MutableList<ChatMessage> = mutableListOf()

    fun connectAndLoad(){
        connectToChatUseCase(
            id = conferenceId,
            callBack = {
                when (it) {
                    is ConnectToChatUseCase.ResultMessage.Success -> {
                        val temp = mutableListOf<ChatMessage>()
                        temp.add(it.chatMessage)
                        messages.add(it.chatMessage)
                        liveDataMessages.postValue(messages)
                    }
                    is ConnectToChatUseCase.ResultMessage.Error ->
                        sendAction(Action.Failure(it.e.message ?: "Ошибка"))
                    else -> sendAction(Action.Failure("Ошибка подключения"))
                }
            }
        ).also{ result ->
            val action = when (result) {
                is ConnectToChatUseCase.ResultConnection.Success ->{
                    loadMessages()
                    return@also
                }
                is ConnectToChatUseCase.ResultConnection.Error ->
                    Action.Failure(result.e.message?: "Ошибка")
                else -> Action.Failure("Ошибка подключения")
            }

            sendAction(action)
        }
    }

    private fun loadMessages(){
        viewModelScope.launch {
            loadChatUseCase(conferenceId).also { result ->
                val action = when (result) {
                    is LoadChatUseCase.Result.Success -> {
                        messages = result.data.toMutableList()
                        liveDataMessages.postValue(messages)
                        //Log.d("!!!", messages[0].user.login)
                        return@also
                    }
                    is LoadChatUseCase.Result.Error ->
                        Action.Failure(result.e.message ?: "Ошибка")
                    else -> Action.Failure("Ошибка подключения")
                }
                sendAction(action)
            }
        }
    }

    fun closeConnection(){
        connectToChatUseCase.closeConnection()
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction){
        is Action.Failure -> {
            Log.d("!!!", "fail")
            val message = viewAction.message
            state.copy(
                isLoading = false,
                isError = true,
                errorMessage = message
            )
        }
        else -> {
            state.copy(
                isLoading = false,
                isError = true,
                errorMessage = "Произошла непредвиденная ошибка"
            )
        }
    }
}