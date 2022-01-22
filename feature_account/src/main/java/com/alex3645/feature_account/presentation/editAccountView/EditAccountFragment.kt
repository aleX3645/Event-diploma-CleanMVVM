package com.alex3645.feature_account.presentation.editAccountView

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.alex3645.base.extension.observe
import com.alex3645.feature_account.domain.model.User
import com.example.feature_account.databinding.FragmentEditAccountBinding
import com.squareup.picasso.Picasso
import java.io.InputStream

class EditAccountFragment : Fragment() {

    private var _binding: FragmentEditAccountBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditAccountViewModel by viewModels()

    var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val observer = Observer<User> {
            user = it

            binding.aboutEditText.text = Editable.Factory().newEditable(it.description)
            binding.emailEditText.text = Editable.Factory().newEditable(it.email)
            binding.nameEditText.text = Editable.Factory().newEditable(it.name)
            binding.surNameEditText.text = Editable.Factory().newEditable(it.surname)
            binding.phoneNumberEditText.text = Editable.Factory().newEditable(it.phone)
        }

        viewModel.user.observe(this, observer)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.stateLiveData, stateObserver)

        initActions()
        viewModel.loadUser()
    }

    private var uriInternal:Uri = Uri.EMPTY


    private val pickImages = registerForActivityResult(ActivityResultContracts.GetContent()){ uri: Uri? ->
        uri?.let { it ->
            uriInternal = it
            Picasso.get().load(it).fit().centerCrop().into(binding.imageViewAccount)
        }
    }

    private fun initActions(){
        binding.backButton.setOnClickListener {
            viewModel.navigateBack(findNavController())
        }

        binding.changeImageButton.setOnClickListener {
            pickImages.launch("image/*")
        }

        binding.saveButton.setOnClickListener {
            user?.let {
                it.name = binding.nameEditText.text.toString()
                it.surname = binding.surNameEditText.text.toString()
                it.description = binding.aboutEditText.text.toString()
                it.phone = binding.phoneNumberEditText.text.toString()
                it.email = binding.emailEditText.text.toString()
                if(uriInternal.path != Uri.EMPTY.path){
                    val stream = activity?.contentResolver?.openInputStream(uriInternal)

                    stream?.let{ it1 ->
                        viewModel.editAccountWithImage(it,it1)
                    }
                }else{
                    viewModel.editAccount(it)
                }
            }
        }
    }

    private val stateObserver = Observer<EditAccountViewModel.ViewState> {
        if(!it.isError){
            viewModel.navigateBack(findNavController())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}