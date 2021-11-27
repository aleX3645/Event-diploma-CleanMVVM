package com.alex3645.feature_conference_detail.presentation.mapView

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alex3645.feature_conference_detail.R
import com.alex3645.feature_conference_detail.databinding.FragmentDetailMapBinding
import com.alex3645.feature_conference_detail.presentation.eventDetailView.EventDetailFragmentArgs
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MapDetailFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: MapViewModel by viewModels()
    private val args by navArgs<MapDetailFragmentArgs>()

    private var parentNavController: NavController? = null

    private lateinit var googleMap: GoogleMap


    private var _binding: FragmentDetailMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActions()

        val mapFragment = childFragmentManager.findFragmentById(R.id.viewMap) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    private fun initActions(){
        binding.backButton.setOnClickListener {
            viewModel.navigateBack(findNavController())
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

        GlobalScope.launch (Dispatchers.Main) {
            if(args.place != ""){
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocationName(args.place, 1)

                if(addresses.size !=0){
                    val point = LatLng(addresses[0].latitude, addresses[0].longitude)

                    googleMap.clear()
                    googleMap.addMarker(MarkerOptions().position(point))

                    val builder: LatLngBounds.Builder = LatLngBounds.Builder();
                    builder.include(point)

                    val bounds: LatLngBounds = builder.build()

                    val padding = 0 // offset from edges of the map in pixels
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

}