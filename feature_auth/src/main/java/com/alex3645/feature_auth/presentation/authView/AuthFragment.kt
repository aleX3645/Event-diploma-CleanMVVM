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
            binding.loginTextField.isActivated = false
            binding.passwordTextField.isActivated = false

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