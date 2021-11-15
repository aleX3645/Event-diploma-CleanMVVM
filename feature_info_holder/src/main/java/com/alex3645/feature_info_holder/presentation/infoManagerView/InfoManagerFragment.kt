package com.alex3645.feature_info_holder.presentation.infoManagerView

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class InfoManagerFragment : Fragment() {

    private val args: InfoManagerFragmentArgs by navArgs()
    private val viewModel: InfoManagerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        when(args.type){
            "noInternet" -> {
                viewModel.navigateToNoInternet(findNavController())
            }
            else -> {}
        }
        super.onCreate(savedInstanceState)
    }
}