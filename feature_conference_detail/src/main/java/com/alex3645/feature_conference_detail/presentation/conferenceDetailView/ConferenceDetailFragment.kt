package com.alex3645.feature_conference_detail.presentation.conferenceDetailView

import android.util.Log
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.alex3645.app.data.api.ServerConstants
import com.alex3645.base.extension.observe
import com.alex3645.feature_conference_detail.R
import com.alex3645.feature_conference_detail.databinding.FragmentConferenceDetailBinding
import com.alex3645.feature_conference_detail.domain.model.Conference
import com.alex3645.feature_conference_detail.presentation.conferenceChatView.ConferenceChatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ConferenceDetailFragment(
    private var conferenceId: Int,
    private var parentNavigationController: NavController,
    private var title: TextView
) : Fragment(), OnMapReadyCallback {

    private val viewModel: ConferenceDetailViewModel by viewModels()

    private lateinit var googleMap: GoogleMap

    private var _binding: FragmentConferenceDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConferenceDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        conferenceId.let{viewModel.conferenceId = it}

        observe(viewModel.stateLiveData, stateObserver)
        initView()
    }

    lateinit var conference: Conference
    private var categories: List<String> = listOf()
    private fun initView(){

        categories = activity?.resources?.let {
            listOf(
                it.getString(R.string.no_category),
                it.getString(R.string.politics),
                it.getString(R.string.society),
                it.getString(R.string.economics),
                it.getString(R.string.sport),
                it.getString(R.string.culture),
                it.getString(R.string.tech),
                it.getString(R.string.science),
                it.getString(R.string.auto),
                it.getString(R.string.others))
        }?: listOf()

        val mapFragment = childFragmentManager.findFragmentById(R.id.conferenceDetailMap) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        viewModel.loadConference(binding.imageConference)

        initActions()
    }

    private fun initActions(){
        binding.registrationButton.setOnClickListener {
            viewModel.navigateToTariffs(parentNavigationController)
        }

        binding.toChatButton.setOnClickListener {
            val intent = Intent(this.context, ConferenceChatActivity::class.java).apply {
                putExtra("id", conferenceId.toLong())
            }
            startActivity(intent)
        }
    }

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap

        this.context?.let{
            if (ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }else{
                googleMap.isMyLocationEnabled = true
            }
        }

        googleMap.setOnMapClickListener {
            viewModel.navigateToMap(parentNavigationController,binding.conferencePlace.text.toString())
        }

        googleMap.uiSettings.setAllGesturesEnabled(false);

        if(binding.conferencePlace.text != ""){
            val geocoder = Geocoder(context, Locale.getDefault())

            GlobalScope.launch (Dispatchers.Main){
                val addresses = geocoder.getFromLocationName(binding.conferencePlace.text.toString(), 1)

                if(addresses.size !=0){
                    val point = LatLng(addresses[0].latitude, addresses[0].longitude)

                    googleMap.clear()
                    googleMap.addMarker(MarkerOptions().position(point))

                    val builder: LatLngBounds.Builder = LatLngBounds.Builder()
                    builder.include(point)

                    val bounds: LatLngBounds = builder.build()

                    val padding = 0
                    val cu: CameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)

                    googleMap.moveCamera(cu)
                }
            }
        }

    }

    private val requestPermissionLauncher =
        registerForActivityResult( ActivityResultContracts.RequestPermission() ) { isGranted: Boolean ->
            if (isGranted) {
                activateLocation()
            }
        }

    private fun activateLocation(){
        this.context?.let {
            if (ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                googleMap.isMyLocationEnabled = true
            }
        }
    }

    private val beautyDateTimeFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    private fun initConference(conference: Conference){
        this.conference = conference

        title.text = if(this.conference.name != "") this.conference.name else context?.getString(R.string.no_data)?:""
        binding.conferenceStartDate.text = if(this.conference.dateStart != "") beautyDateTimeFormatter.format(ServerConstants.serverDateTimeFormat.parse(this.conference.dateStart)) else context?.getString(R.string.no_data)?:""
        binding.conferenceEndDate.text = if(this.conference.dateEnd != "") beautyDateTimeFormatter.format(ServerConstants.serverDateTimeFormat.parse(this.conference.dateEnd)) else context?.getString(R.string.no_data)?:""
        binding.conferencePlace.text = if(this.conference.location != "") this.conference.location else context?.getString(R.string.no_data)?:""
        binding.conferenceCategory.text = if(this.conference.category.toString() != "") categories[this.conference.category] else context?.getString(R.string.no_data)?:""
        binding.conferenceDescription.text = if(this.conference.description != "") this.conference.description else context?.getString(R.string.no_data)?:""
    }

    private val stateObserver = Observer<ConferenceDetailViewModel.ViewState> {

        if(it.isError){
            Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
        }else{
            initConference(it.conference!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}