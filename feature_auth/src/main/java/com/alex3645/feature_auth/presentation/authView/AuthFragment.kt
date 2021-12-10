package com.alex3645.feature_auth.presentation.authView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.alex3645.base.extension.observe
import com.alex3645.feature_auth.R
import com.alex3645.feature_auth.databinding.FragmentAuthBinding
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

        initActions()
    }

    private fun initActions(){
        binding.loginButton.setOnClickListener {
            binding.loginTextField.alpha = .5f
            binding.loginTextField.clearFocus()

            binding.passwordTextField.alpha = .5f
            binding.passwordTextField.clearFocus()

            binding.loginButton.alpha = .5f
            binding.registerButton.alpha = .5f

            if(binding.orgSwitch.isChecked){
                viewModel.tryAuthAsOrganizer(PasswordAuthentication(
                    binding.loginTextField.editText?.text.toString(),
                    binding.passwordTextField.editText?.text.toString().toCharArray()))
            }else{
                viewModel.tryAuthAsUser(PasswordAuthentication(
                    binding.loginTextField.editText?.text.toString(),
                    binding.passwordTextField.editText?.text.toString().toCharArray()))
            }
        }

        binding.registerButton.setOnClickListener {
            viewModel.navigateToReg(findNavController())
        }
    }

    private val stateObserver = Observer<AuthViewModel.ViewState> {

        if(it.isError){
            context?.let{it1->
                Toast.makeText(it1, it1.resources.getText(R.string.auth_error), Toast.LENGTH_LONG).show()
            }

            binding.loginTextField.alpha = 1.0f
            binding.loginTextField.clearFocus()

            binding.passwordTextField.alpha = 1.0f
            binding.passwordTextField.clearFocus()

            binding.loginButton.alpha = 1.0f
            binding.registerButton.alpha = 1.0f
        }else{
            viewModel.setRememberFlag(binding.rememberSwitch.isChecked)
            viewModel.navigateToAccount(findNavController())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}