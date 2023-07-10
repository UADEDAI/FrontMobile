package com.uade.daitp.client.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.uade.daitp.R
import com.uade.daitp.client.presentation.adapters.ClientMovieAdapter
import com.uade.daitp.client.presentation.bottomSheet.CinemasBottomSheet
import com.uade.daitp.client.presentation.bottomSheet.FilterBottomSheet
import com.uade.daitp.databinding.FragmentClientHomeMoviesBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.models.emptyMovieList
import com.uade.daitp.presentation.util.errorDialog
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class ClientHomeMoviesListFragment : Fragment(R.layout.fragment_client_home_movies),
    FilterBottomSheet.FilterListener,
    CinemasBottomSheet.CinemasListener {

    private val viewModel = ViewModelDI.getHomeMoviesViewModel()
    private lateinit var binding: FragmentClientHomeMoviesBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var filterBottomSheet = FilterBottomSheet(this)
    private var cinemasBottomSheet = CinemasBottomSheet(this)

    private var editMode = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentClientHomeMoviesBinding.bind(view)

        binding.clientHomeMovieSearchButton.setOnClickListenerWithThrottle { toggleEditMode() }

        binding.clientHomeMovieFilter.setOnClickListenerWithThrottle {
            showFilter()
        }

        binding.clientHomeMovieText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.search(textView.text.toString())
                textView.clearFocus()
                hideKeyboard()
                true
            } else {
                false
            }
        }

        requestLocationPermission()
        initializeLocationClient()

        val recyclerView = binding.clientHomeMovieList
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = ClientMovieAdapter(emptyMovieList())

        viewModel.filteredMovies.observe(viewLifecycleOwner) {
            (recyclerView.adapter as ClientMovieAdapter).refreshMovies(it)

            binding.clientHomeMovieEmpty.visibility =
                if (it.showing.isEmpty()) VISIBLE else GONE
        }

        (recyclerView.adapter as ClientMovieAdapter).selectedMovie.observe(viewLifecycleOwner) {
            viewModel.getCinemas(it)
        }

        viewModel.nearCinemas.observe(viewLifecycleOwner) {
            showCinemas(it)
        }
    }

    override fun onFilterSelected(distance: Double, genre: String?, score: Double?) {
        viewModel.setFilters(distance, genre, score)
        filterBottomSheet.dismiss()
    }

    override fun clearFilters() {
        binding.clientHomeMovieText.setText("")
        viewModel.clearFilters()
        filterBottomSheet.dismiss()
        toggleEditMode()
    }

    override fun onCinemaSelected(cinema: Cinema?) {
        cinemasBottomSheet.dismiss()
        cinema?.let {
            view?.let { view ->
                val bundle = Bundle()
                bundle.putInt(CINEMA_ID, it.id)
                bundle.putInt(MOVIE_ID, viewModel.selectedMovie.value!!.id)
                view.findNavController()
                    .navigate(R.id.action_clientHomeFragment_to_clientMoviePagerFragment, bundle)
            }
        } ?: run {
            errorDialog(getString(R.string.select_cinema_error))
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.refreshData()
    }

    private fun toggleEditMode() {
        editMode = !editMode
        if (editMode) {
            binding.clientHomeMovieFilter.visibility = VISIBLE
            binding.clientHomeMovieSearch.visibility = VISIBLE

            binding.clientHomeMovieTitle.visibility = GONE
            binding.clientHomeMovieSearchButton.visibility = GONE
        } else {
            binding.clientHomeMovieFilter.visibility = GONE
            binding.clientHomeMovieSearch.visibility = GONE

            binding.clientHomeMovieTitle.visibility = VISIBLE
            binding.clientHomeMovieSearchButton.visibility = VISIBLE
        }
    }

    private fun showFilter() {
        filterBottomSheet.show(parentFragmentManager, FilterBottomSheet.TAG)
    }

    private fun showCinemas(cinemas: List<Cinema>) {
        cinemasBottomSheet.setCinemas(cinemas)
        cinemasBottomSheet.show(parentFragmentManager, CinemasBottomSheet.TAG)
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
                viewModel.setLocation(location.latitude, location.longitude)
                Log.d("UserLocation", "${location.latitude} : ${location.longitude}")
            }
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    companion object {
        const val MOVIE_ID = "movie_id"
        const val CINEMA_ID = "cinema_id"
    }
}