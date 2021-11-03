package com.alex3645.feature_conference_detail.usecase

import android.util.Log
import com.alex3645.feature_conference_detail.data.model.ChatMessage
import com.alex3645.feature_conference_detail.domain.repository.ConferenceDetailRepository
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import java.lang.Exception
import javax.inject.Inject




class ConnectToChatUseCase @Inject constructor(private val repository: ConferenceDetailRepository, private val gson: Gson){
    interface ResultConnection {
        object Success : ResultConnection
        data class Error(val e: Throwable) : ResultConnection
    }

    interface ResultMessage {
        data class Success(val chatMessage: ChatMessage) : ResultMessage
        data class Error(val e: Throwable) : ResultMessage
    }

    //private val okClient = OkHttpClient()
    private lateinit var callBack: (result: ResultMessage)->Unit

    private val compositeDisposable = CompositeDisposable()
    lateinit var stompClient: StompClient
    operator fun invoke(id: Long, callBack: (result: ResultMessage)->Unit) : ResultConnection{
        return try{
            this.callBack = callBack

            val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://10.152.43.33:7777/ws/websocket")

            val topic = stompClient.topic("/chat/$id/queue/messages")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { topicMessage ->
                try {
                    Log.d("!!!", topicMessage.payload.subSequence(1,topicMessage.payload.length-2).toString().replace("\\",""))
                    val chatMessage = gson.fromJson(topicMessage.payload.subSequence(1,topicMessage.payload.length-1).toString().replace("\\",""), ChatMessage::class.java)
                    Log.d("!!!", topicMessage.payload)
                    callBack.invoke(ResultMessage.Success(chatMessage = chatMessage))
                } catch (e: Exception) {
                    e.message?.let { Log.d("!!!", it) }
                    callBack.invoke(ResultMessage.Error(e))
                }
            }


            compositeDisposable.add(topic)

            stompClient.connect()

            //val request = Request.Builder().url(ServerConstants.LOCAL_SERVER + "/api/chat/chatId/" + id.toString() + "/conDest").build()

            //val request = Request.Builder().url("http://10.152.43.33:7777" + "/ws/websocket").build()
/*
            val listener = WebSocketListener {
                try {
                    val chatMessage = gson.fromJson(it, ChatMessage::class.java)
                    callBack.invoke(ResultMessage.Success(chatMessage = chatMessage))
                } catch (e: Exception) {
                    callBack.invoke(ResultMessage.Error(e))
                }
            }
*/
            //val ws = okClient.newWebSocket(request, listener)
            return ResultConnection.Success
        }catch (e: Exception){
            ResultConnection.Error(e)
        }
    }

    fun closeConnection() {
        stompClient.disconnect()

        //if (mRestPingDisposable != null) mRestPingDisposable.dispose();
        compositeDisposable.dispose()
    }
}