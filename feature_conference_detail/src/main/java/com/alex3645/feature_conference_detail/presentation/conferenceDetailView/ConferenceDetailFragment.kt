package com.alex3645.feature_conference_detail.presentation.conferenceDetailView

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alex3645.base.extension.observe
import com.alex3645.feature_conference_detail.R
import com.alex3645.feature_conference_detail.databinding.FragmentConferenceDetailBinding
import com.alex3645.feature_conference_detail.domain.model.Conference
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class ConferenceDetailFragment(): Fragment(), OnMapReadyCallback {

    private val viewModel: ConferenceDetailViewModel by viewModels()

    private lateinit var googleMap: GoogleMap

    private var conferenceId: Int? = null
    constructor(id: Int) : this() {
        conferenceId = id
    }

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

        conferenceId?.let{viewModel.conferenceId = it}

        observe(viewModel.stateLiveData, stateObserver)
        initView()
    }

    lateinit var conference: Conference
    private fun initView(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.conferenceDetailMap) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        viewModel.loadConference()

        initActions()
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

        googleMap.setOnCameraMoveListener {
            binding.detailScrollView.setScrollingEnabled(false)
        }

        googleMap.setOnCameraIdleListener {
            binding.detailScrollView.setScrollingEnabled(true)
        }

        if(binding.conferencePlace.text != ""){
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocationName(binding.conferencePlace.text.toString(), 1)

            if(addresses.size !=0){
                val point = LatLng(addresses[0].latitude, addresses[0].longitude)

                googleMap.clear()
                googleMap.addMarker(MarkerOptions().position(point))

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(point))
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
            if (ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }else{
                googleMap.isMyLocationEnabled = true
            }
        }
    }

    private val stateObserver = Observer<ConferenceDetailViewModel.ViewState> {

        binding.conferenceProgressBar.isVisible = it.isLoading
        if(it.isError){
            Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
        }else{
            initConference(it.conference!!)
        }
    }

    private fun initConference(conference: Conference){
        this.conference = conference

        binding.conferenceTitle.text = this.conference.name
        binding.conferenceStartDate.text = this.conference.dateStart
        binding.conferenceEndDate.text = this.conference.dateEnd
        binding.conferencePlace.text = this.conference.location
        binding.conferenceCategory.text = this.conference.category.toString()
        binding.conferenceDescription.text = this.conference.description
    }

    private fun initActions(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}