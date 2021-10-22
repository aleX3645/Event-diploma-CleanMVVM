package com.alex3645.feature_auth.presentation.registrationView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.alex3645.base.extension.observe
import com.alex3645.feature_auth.data.model.UserRegJson
import com.alex3645.feature_auth.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {
    private val viewModel: RegistrationViewModel by viewModels()

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
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
        binding.regButton.setOnClickListener {
            binding.registrationProgressBar.isVisible = true
            val userJson = UserRegJson(
                binding.loginRegistrationTextField.editText?.text.toString(),
                binding.passwordTextField.editText?.text.toString(),
                binding.nameRegistrationTextField.editText?.text.toString(),
                binding.surNameRegistrationTextField.editText?.text.toString(),
                binding.aboutRegistrationTextField.editText?.text.toString(),
                binding.phoneNumberRegistrationTextView.editText?.text.toString(),
                binding.emailRegistraionTextField.editText?.text.toString())

            if(binding.regOrgSwitch.isChecked){
                viewModel.tryRegAsOrganizer(userJson)
            }else{
                viewModel.tryRegAsUser(userJson)
            }
        }
    }

    private val stateObserver = Observer<RegistrationViewModel.ViewState> {

        binding.registrationProgressBar.isVisible = it.isLoading
        if(it.isError){
            Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
        }else{
            viewModel.navigateToAccount(findNavController())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}