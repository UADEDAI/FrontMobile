package com.uade.daitp.client.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.uade.daitp.R
import com.uade.daitp.client.presentation.adapters.ClientMovieAdapter
import com.uade.daitp.databinding.FragmentClientHomeMoviesBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.owner.home.core.models.emptyMovieList
import com.uade.daitp.presentation.util.errorDialog

class ClientHomeMoviesListFragment : Fragment(R.layout.fragment_client_home_movies) {

    private val viewModel = ViewModelDI.getHomeMoviesViewModel()
    private lateinit var binding: FragmentClientHomeMoviesBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentClientHomeMoviesBinding.bind(view)

        requestLocationPermission()
        initializeLocationClient()

        val recyclerView = binding.clientHomeMovieList
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = ClientMovieAdapter(emptyMovieList())

        viewModel.filteredMovies.observe(viewLifecycleOwner) {
            (recyclerView.adapter as ClientMovieAdapter).refreshMovies(it)

            binding.clientHomeMovieEmpty.visibility =
                if (it.showing.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.refreshData()
    }

    private fun requestLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    initializeLocationClient()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    initializeLocationClient()
                }
                else -> {
                    errorDialog(getString(R.string.no_location))
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun initializeLocationClient() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(requireActivity())
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                Log.d("TESTYTEST", "${location.latitude} : ${location.longitude}")
            }
        }
    }

    companion object {
        const val BOOKING_ID = "booking_id"
    }
}