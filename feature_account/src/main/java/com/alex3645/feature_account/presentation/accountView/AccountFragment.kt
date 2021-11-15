package com.alex3645.feature_account.presentation.accountView

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.alex3645.base.extension.observe
import com.alex3645.feature_account.domain.model.User
import com.example.feature_account.databinding.FragmentAccountBinding

class AccountFragment: Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if(!viewModel.isUserAuthed()){
            viewModel.navigateToAccountAuth(findNavController())
        }

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initView(user: User){
        binding.settingsButton.setOnClickListener {
            viewModel.navigateToSettings(findNavController())
        }

        binding.accountDescription.text = user.description
        binding.accountEmail.text = user.email
        binding.accountLogin.text = user.login
        binding.accountPhone.text = user.phone
        binding.accountNameSurname.text = StringBuffer(user.name + " " + user.surname)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.stateLiveData, stateObserver)

        viewModel.loadUser()
    }

    private val stateObserver = Observer<AccountViewModel.ViewState> {
        if(!it.isError){
            initView(it.user!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}