package com.alex3645.feature_conference_detail.presentation.conferenceChatView

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.alex3645.base.extension.observe
import com.alex3645.feature_conference_detail.R
import com.alex3645.feature_conference_detail.data.model.ChatMessage
import com.alex3645.feature_conference_detail.di.component.DaggerConferenceDetailFragmentComponent
import com.alex3645.feature_conference_detail.presentation.conferenceChatView.chatView.ChatMessageIncomingViewHolder
import com.alex3645.feature_conference_detail.presentation.conferenceChatView.chatView.ChatMessageOutcomingViewHolder
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessageHolders
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessagesList
import com.stfalcon.chatkit.messages.MessagesListAdapter
import java.lang.Exception

class ConferenceChatActivity() : AppCompatActivity(){

    private val viewModel: ConferenceChatViewModel by viewModels()

    private var conferenceId: Long? = null

    private lateinit var input: MessageInput
    private lateinit var messagesList: MessagesList
    private lateinit var backButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val b = intent.extras
        if(b!=null){
            conferenceId = b.getInt("conferenceId").toLong()
        }

        viewModel.conferenceId = conferenceId?:0

        setContentView(R.layout.activity_conference_chat)
        input = findViewById(R.id.input)
        messagesList = findViewById(R.id.messagesList)
        backButton = findViewById(R.id.backButtonChat)

        var firstTimeFlag = true
        val observer = Observer<MutableList<ChatMessage>> {
            if(firstTimeFlag){
                messagesAdapter.addToEnd(it,true)
                firstTimeFlag = false
            }else{
                messagesAdapter.addToStart(it[it.size-1],true)
            }
        }


        viewModel.liveDataMessages.observe(this,observer)

        injectDependency()
    }

    private fun injectDependency() {
        DaggerConferenceDetailFragmentComponent.factory().create().inject(this)
    }

    override fun onStart() {
        super.onStart()

        conferenceId?.let{viewModel.conferenceId = it}

        observe(viewModel.stateLiveData, stateObserver)

        viewModel.connectAndLoad()

        initAdapter()
        initActions()
    }

    lateinit var messagesAdapter: MessagesListAdapter<ChatMessage>

    private var imageLoader = ImageLoader { imageView: ImageView?, url: String?, _: Any? ->
        if (url != null) {
            if(url.isNotEmpty()){
                Picasso.get().load(url).centerCrop().fit().into(imageView)
            }
        }
    }

    private fun initAdapter(){
        val holdersConfig = MessageHolders()
            .setIncomingTextConfig(ChatMessageIncomingViewHolder::class.java,R.layout.item_custom_incoming_text_message)
            .setOutcomingTextConfig(ChatMessageOutcomingViewHolder::class.java, R.layout.item_custom_outcoming_text_message)

        messagesAdapter = MessagesListAdapter <ChatMessage> (viewModel.username, holdersConfig,imageLoader)
        messagesList.setAdapter(messagesAdapter)
    }

    private fun initActions(){
        input.setInputListener { input ->
            viewModel.sendMessage(input.toString())
            true
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private val stateObserver = Observer<ConferenceChatViewModel.ViewState> {
        if(it.isError){
            Toast.makeText(this.application, it.errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.closeConnection()
    }
}