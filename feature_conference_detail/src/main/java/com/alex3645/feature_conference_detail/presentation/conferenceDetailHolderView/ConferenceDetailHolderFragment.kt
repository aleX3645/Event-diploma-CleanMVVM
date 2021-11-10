package com.alex3645.feature_conference_detail.presentation.conferenceDetailHolderView

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alex3645.feature_conference_detail.R
import com.alex3645.feature_conference_detail.databinding.FragmentConferenceDetailHolderBinding
import com.alex3645.feature_conference_detail.presentation.conferenceChatView.ConferenceChatFragment
import com.alex3645.feature_conference_detail.presentation.conferenceDetailView.ConferenceDetailFragment
import com.alex3645.feature_conference_detail.presentation.eventRecyclerView.EventRecyclerFragment
import com.google.android.material.tabs.TabLayoutMediator


class ConferenceDetailHolderFragment: Fragment()  {
    private val numPages = 3

    private val args by navArgs<ConferenceDetailHolderFragmentArgs>()
    private val viewModel: ConferenceDetailHolderViewModel by viewModels()

    private var _binding: FragmentConferenceDetailHolderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        _binding = FragmentConferenceDetailHolderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.conferenceId = args.conferenceId.toLong()
        initActions()

        binding.pager.adapter = this.activity?.let { ScreenSlidePagerAdapter(it) }
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            when (position) {
                0 -> {
                    tab.icon = context?.let { AppCompatResources.getDrawable(it,R.drawable.ic_info) }
                    tab.text = "Инфо"
                }
                1 -> {
                    tab.icon = context?.let { AppCompatResources.getDrawable(it,R.drawable.ic_schedule) }
                    tab.text = "Расписание"
                }
                2 -> {
                    tab.icon = context?.let { AppCompatResources.getDrawable(it,R.drawable.ic_chat) }
                    tab.text = "Чат"
                }
            }
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.conference_detail_upper_appbar_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                viewModel.navigateToSettings(findNavController())
                true
            }
            else -> false
        }
    }

    private fun initActions(){
        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.pager.currentItem == 0) {
                    this.isEnabled = false
                    activity?.onBackPressed()
                } else {
                    binding.pager.currentItem = binding.pager.currentItem - 1
                }
            }
        })
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = numPages

        override fun createFragment(position: Int): Fragment = when(position){
            (0)-> ConferenceDetailFragment(args.conferenceId, findNavController())
            (1)-> EventRecyclerFragment(args.conferenceId)
            (2)-> ConferenceChatFragment(args.conferenceId.toLong())
            else -> ConferenceDetailFragment()
        }
    }
}