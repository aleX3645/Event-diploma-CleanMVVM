package com.alex3645.feature_conference_list.presentation.searchView

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alex3645.feature_conference_list.presentation.registrationView.RegistrationViewModel
import com.alex3645.feature_event_list.R
import com.alex3645.feature_event_list.databinding.FragmentRegistrationBinding
import com.alex3645.feature_event_list.databinding.FragmentSearchBinding

class SearchFragment : Fragment(), SearchView.OnQueryTextListener{
    //private val viewModel: RegistrationViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    lateinit var searchView: SearchView
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.search_upper_appbar_menu,menu)
        menu.findItem(R.id.searchViewItem).expandActionView()
        searchView = menu.findItem(R.id.searchViewItem).actionView as SearchView
        /*
        searchView.setOnCloseListener {

        }*/

        //searchView.
    }

    override fun onQueryTextChange(newText: String?): Boolean {

    }



}