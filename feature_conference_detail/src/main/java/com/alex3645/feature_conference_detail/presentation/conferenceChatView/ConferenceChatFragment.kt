package com.alex3645.feature_conference_detail.presentation.conferenceChatView

import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex3645.base.extension.observe
import com.alex3645.feature_conference_detail.data.model.ChatMessage
import com.alex3645.feature_conference_detail.databinding.FragmentConferenceChatBinding
import com.alex3645.feature_conference_detail.databinding.FragmentConferenceDetailBinding
import com.alex3645.feature_conference_detail.di.component.DaggerConferenceDetailFragmentComponent
import com.alex3645.feature_conference_detail.presentation.conferenceChatView.recyclerView.ChatRecyclerAdapter
import com.alex3645.feature_conference_detail.presentation.conferenceDetailView.ConferenceDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConferenceChatFragment() : Fragment(){

    private val viewModel: ConferenceChatViewModel by viewModels()

    private var conferenceId: Long? = null
    constructor(id: Long) : this() {
        conferenceId = id
    }

    private var _binding: FragmentConferenceChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val observer = Observer<MutableList<ChatMessage>> {
            chatRecyclerAdapter.messages = it
        }

        viewModel.liveDataMessages.observe(this,observer)

        injectDependency()
    }

    private fun injectDependency() {
        DaggerConferenceDetailFragmentComponent.factory().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConferenceChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Inject
    lateinit var chatRecyclerAdapter: ChatRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        conferenceId?.let{viewModel.conferenceId = it}

        observe(viewModel.stateLiveData, stateObserver)

        viewModel.connectAndLoad()

        initView()
        initActions()
    }

    private fun initView(){
        binding.eventRecyclerView.adapter = chatRecyclerAdapter
        binding.eventRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun initActions(){
        binding.buttonSS.setOnClickListener {
            viewModel.sendMessage(binding.editText.text.toString())
            binding.editText.text.clear()
        }
        binding.editText.setOnEditorActionListener(object: TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    viewModel.sendMessage(binding.editText.text.toString())
                    binding.editText.text.clear()
                    return true
                }
                return false
            }
        })
    }

    private val stateObserver = Observer<ConferenceChatViewModel.ViewState> {

        binding.swipeEventContainer.isRefreshing = it.isLoading
        if(it.isError){
            Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.closeConnection()
    }
}