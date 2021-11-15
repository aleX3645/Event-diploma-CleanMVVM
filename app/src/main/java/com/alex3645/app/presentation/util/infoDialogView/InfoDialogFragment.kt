package com.alex3645.app.presentation.util.infoDialogView

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.alex3645.eventdiploma_mvvm.databinding.FragmentDialogInfoBinding

class InfoDialogFragment : DialogFragment() {

    private var _binding: FragmentDialogInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun show(fragmentManager: FragmentManager, tag: String, message: String, type: String){
        this.show(fragmentManager, tag)
    }
}