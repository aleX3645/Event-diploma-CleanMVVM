package com.alex3645.feature_conference_list.presentation.authView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.alex3645.base.extension.observe
import com.alex3645.feature_auth.databinding.FragmentAuthBinding
import com.alex3645.feature_auth.presentation.authView.AuthViewModel
import java.net.PasswordAuthentication

class AuthFragment : Fragment() {
    private val viewModel: AuthViewModel by viewModels()

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.stateLiveData, stateObserver)

        initView()
    }

    private fun initView(){

        initActions()
    }

    private fun initActions(){
        binding.loginButton.setOnClickListener {
            binding.loginTextField.isActivated = false
            binding.passwordTextField.isActivated = false
            binding.registrationProgressBar.isVisible = true
            viewModel.tryAuth(PasswordAuthentication(
                binding.loginTextField.editText?.text.toString(),
                binding.passwordTextField.editText?.text.toString().toCharArray()))
        }

        binding.registerButton.setOnClickListener {
            viewModel.navigateToReg(findNavController())
        }
    }

    private val stateObserver = Observer<AuthViewModel.ViewState> {

        binding.registrationProgressBar.isVisible = it.isLoading
        if(it.isError){
            Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
            binding.loginTextField.isActivated = true
            binding.passwordTextField.isActivated = true
        }else{
            viewModel.navigateToAccount(findNavController())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}