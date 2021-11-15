package com.alex3645.feature_user_account.presentation.userAccountView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alex3645.base.extension.observe
import com.alex3645.feature_user_account.databinding.FragmentUserAccountBinding
import com.alex3645.feature_user_account.domain.data.User

class UserAccountFragment: Fragment() {
    private val viewModel: UserAccountViewModel by viewModels()

    private var _binding: FragmentUserAccountBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UserAccountFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.stateLiveData, stateObserver)

        initActions()

        viewModel.loadUserById(args.userId)
    }

    private fun initActions(){
        binding.backButton.setOnClickListener {
            viewModel.navigateBack(findNavController())
        }
    }

    private fun initAccount(user: User){
        binding.accountDescription.text = user.description
        binding.accountEmail.text = user.email
        binding.accountLogin.text = user.login
        binding.accountPhone.text = user.phone
    }

    private val stateObserver = Observer<UserAccountViewModel.ViewState> {
        if(it.isError){
            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        }else{
            initAccount(it.user!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}