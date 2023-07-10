package com.uade.daitp.client.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.uade.daitp.R
import com.uade.daitp.client.core.model.ScreeningClient
import com.uade.daitp.client.presentation.adapters.ScreeningsClientAdapter
import com.uade.daitp.databinding.DialogTicketsBinding
import com.uade.daitp.databinding.FragmentClientMovieScreeningsBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.presentation.util.Utils.distance
import com.uade.daitp.presentation.util.errorDialog
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle
import java.lang.Double.parseDouble
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.*

class ClientScreeningListFragment : Fragment(R.layout.fragment_client_movie_screenings) {

    private val viewModel = ViewModelDI.getClientScreeningListViewModel()
    private lateinit var binding: FragmentClientMovieScreeningsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var latitute = 0.0
    private var longitude = 0.0
    private var currentDate = Calendar.getInstance()

    private val friendlyFormat = SimpleDateFormat("dd, MMMM", Locale.getDefault())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentClientMovieScreeningsBinding.bind(view)

        val movieId = arguments?.getInt(ClientHomeMoviesListFragment.MOVIE_ID)
        val cinemaId = arguments?.getInt(ClientHomeMoviesListFragment.CINEMA_ID)
        movieId?.let {
            cinemaId?.let {
                viewModel.getCinemaBy(cinemaId)
                viewModel.saveData(movieId, cinemaId)
                viewModel.getScreenings(currentDate.time)
            }
        }

        binding.screeningDate.text = toFriendlyDateString(currentDate.time)

        val recyclerView = binding.screeningList
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ScreeningsClientAdapter()

        viewModel.screenings.observe(viewLifecycleOwner) {
            (recyclerView.adapter as ScreeningsClientAdapter).refreshData(it)
        }

        (recyclerView.adapter as ScreeningsClientAdapter).selectedScreening.observe(
            viewLifecycleOwner
        ) {
            showDialog(it)
        }

        binding.screeningTimeButton.setOnClickListenerWithThrottle {
            (recyclerView.adapter as ScreeningsClientAdapter).sortToggle()
        }

        viewModel.cinema.observe(viewLifecycleOwner) {
            binding.screeningCinemaName.text = it.name
            binding.screeningCinemaAddress.text = it.getAddress()
            calculate()
        }

        binding.screeningDateButton.setOnClickListenerWithThrottle {
            buildDatePicker()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            errorDialog()
        }

        requestLocationPermission()
    }

    private fun showDialog(screeningClient: ScreeningClient) {
        val dialogBinding = DialogTicketsBinding.inflate(
            LayoutInflater.from(requireContext()),
            binding.root,
            false
        )
        MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(getString(R.string.accept)) { _, _ ->
                val numberOfTickets = dialogBinding.dialogNumber.text.toString().toInt()
                if (numberOfTickets > screeningClient.availableSeats) {
                    errorDialog(getString(R.string.not_enough_tickets))
                    return@setPositiveButton
                }
                if (numberOfTickets > 0) {
                    val screeningCalendar = Calendar.getInstance()
                    screeningCalendar.time = screeningClient.startAt
                    currentDate.set(Calendar.HOUR, screeningCalendar.get(Calendar.HOUR))
                    currentDate.set(Calendar.MINUTE, screeningCalendar.get(Calendar.MINUTE))
                    viewModel.getTickets(
                        numberOfTickets,
                        currentDate.timeInMillis,
                        screeningClient
                    )
                    binding.root.findNavController()
                        .navigate(R.id.action_clientMoviePagerFragment_to_clientMovieSeatingFragment)
                } else {
                    errorDialog(getString(R.string.tickets_zero))
                }
            }
            .show()
    }

    private fun buildDatePicker() {
        val start = Calendar.getInstance()
        val end = Calendar.getInstance()
        end.add(Calendar.MONTH, 1)
        val calendarConstraints = CalendarConstraints.Builder()
            .setStart(start.timeInMillis)
            .setEnd(end.timeInMillis)
            .build()
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setSelection(currentDate.timeInMillis)
                .setCalendarConstraints(calendarConstraints)
                .build()
        datePicker.show(parentFragmentManager, "DatePicker")
        datePicker.addOnPositiveButtonClickListener {
            currentDate.timeInMillis = it
            currentDate.add(Calendar.HOUR, 3)
            viewModel.getScreenings(currentDate.time)

            binding.screeningDate.text = toFriendlyDateString(currentDate.time)
        }
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
                latitute = location.latitude
                longitude = location.longitude

                calculate()
            }
        }
    }

    private fun toFriendlyDateString(date: Date): String {
        return friendlyFormat.format(date)
    }

    @SuppressLint("SetTextI18n")
    private fun calculate() {
        if (latitute != 0.0 && viewModel.cinema.isInitialized) {
            val cinema: Cinema = viewModel.cinema.value!!
            val distance = distance(
                latitute,
                longitude,
                cinema.latitude.toDouble(),
                cinema.longitude.toDouble()
            )
            binding.screeningCinemaDistance.text = "${
                distance.toString().substring(0, 2)
            }km"
        }
    }

    private fun String?.toDouble(): Double {
        if (this == null || this.isEmpty()) return 0.0
        return parseDouble(this)
    }

    private fun String?.toInt(): Int {
        if (this == null || this.isEmpty()) return 0
        return parseInt(this)
    }
}