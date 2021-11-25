package com.alex3645.feature_conference_detail.presentation.conferenceDetailView

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.alex3645.base.extension.observe
import com.alex3645.feature_conference_detail.R
import com.alex3645.feature_conference_detail.databinding.FragmentConferenceDetailBinding
import com.alex3645.feature_conference_detail.domain.model.Conference
import com.alex3645.feature_conference_detail.presentation.conferenceChatView.ConferenceChatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class ConferenceDetailFragment(): Fragment(), OnMapReadyCallback {

    private val viewModel: ConferenceDetailViewModel by viewModels()

    private var parentNavController: NavController? = null

    private lateinit var googleMap: GoogleMap

    private var conferenceId: Int? = null
    constructor(id: Int, parentNavController: NavController) : this() {
        conferenceId = id
        this.parentNavController = parentNavController
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
        viewModel.loadConference(binding.imageConference)

        initActions()
    }

    private fun initActions(){
        binding.registrationButton.setOnClickListener {
            parentNavController?.let { it1 -> viewModel.navigateToTariffs(it1) }
        }

        binding.toChatButton.setOnClickListener {
            val intent = Intent(this.context, ConferenceChatActivity::class.java).apply {
                conferenceId?.let { it1 -> putExtra("id", it1.toLong()) }
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
            if (ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                googleMap.isMyLocationEnabled = true
            }
        }
    }

    private fun initConference(conference: Conference){
        this.conference = conference

        binding.conferenceTitle.text = if(this.conference.name != "") this.conference.name else context?.getString(R.string.no_data)?:""
        binding.conferenceStartDate.text = if(this.conference.dateStart != "") this.conference.dateStart else context?.getString(R.string.no_data)?:""
        binding.conferenceEndDate.text = if(this.conference.dateEnd != "") this.conference.dateEnd else context?.getString(R.string.no_data)?:""
        binding.conferencePlace.text = if(this.conference.location != "") this.conference.location else context?.getString(R.string.no_data)?:""
        binding.conferenceCategory.text = if(this.conference.category.toString() != "") this.conference.category.toString() else context?.getString(R.string.no_data)?:""
        binding.conferenceDescription.text = if(this.conference.description != "") this.conference.description else context?.getString(R.string.no_data)?:""
    }

    private val stateObserver = Observer<ConferenceDetailViewModel.ViewState> {

        binding.conferenceProgressBar.isVisible = it.isLoading
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