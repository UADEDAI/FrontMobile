package com.uade.daitp.owner.home.presentation

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentOwnerCinemaBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.owner.home.core.models.Screening
import com.uade.daitp.owner.home.core.models.emptyMovieList
import com.uade.daitp.owner.home.core.models.enums.ScreeningFormat
import com.uade.daitp.owner.home.presentation.adapters.CinemaRoomAdapter
import com.uade.daitp.owner.home.presentation.adapters.MoviesAdapter
import com.uade.daitp.owner.home.presentation.adapters.ScreeningAdapter
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle
import java.text.SimpleDateFormat
import java.util.*

class OwnerCinemaFragment : Fragment(R.layout.fragment_owner_cinema) {

    private val viewModel: OwnerCinemaViewModel = ViewModelDI.getOwnerCinemaViewModel()
    private lateinit var binding: FragmentOwnerCinemaBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOwnerCinemaBinding.bind(view)

        val cinemaId = arguments?.getInt(CINEMA_ID)
        cinemaId?.let { id ->
            viewModel.getCinemaBy(id)
            viewModel.getCinemaRoomsById(id)
        }

        val recyclerView = binding.homeRoomList
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.homeCinemaBack.setOnClickListenerWithThrottle {
            view.findNavController().popBackStack()
        }

        binding.homeRoomEdit.setOnClickListenerWithThrottle {
            val bundle = Bundle()
            bundle.putInt(CINEMA_ID, cinemaId!!)
            bundle.putInt(CINEMA_ROOM_ID, viewModel.selectedCinemaRoom.value!!.id)
            view.findNavController()
                .navigate(R.id.action_ownerCinemaFragment_to_ownerCinemaRoomFormFragment, bundle)
        }

        binding.homeRoomAdd.setOnClickListenerWithThrottle {
            val bundle = Bundle()
            bundle.putInt(CINEMA_ID, cinemaId!!)
            view.findNavController()
                .navigate(R.id.action_ownerCinemaFragment_to_ownerCinemaRoomFormFragment, bundle)
        }

        binding.homeMovieAdd.setOnClickListenerWithThrottle {
            val bundle = Bundle()
            bundle.putInt(CINEMA_ID, cinemaId!!)
            bundle.putInt(CINEMA_ROOM_ID, viewModel.selectedCinemaRoom.value!!.id)
            view.findNavController()
                .navigate(R.id.action_ownerCinemaFragment_to_ownerMovieManagerFragment, bundle)
        }

        binding.homeCinemaEmptyButton.setOnClickListenerWithThrottle {
            if (!viewModel.selectedCinemaRoom.isInitialized) {
                val bundle = Bundle()
                bundle.putInt(CINEMA_ID, cinemaId!!)
                view.findNavController()
                    .navigate(
                        R.id.action_ownerCinemaFragment_to_ownerCinemaRoomFormFragment,
                        bundle
                    )
            } else {
                val bundle = Bundle()
                bundle.putInt(CINEMA_ID, cinemaId!!)
                bundle.putInt(CINEMA_ROOM_ID, viewModel.selectedCinemaRoom.value!!.id)
                view.findNavController()
                    .navigate(R.id.action_ownerCinemaFragment_to_ownerMovieManagerFragment, bundle)
            }
        }

        viewModel.cinema.observe(viewLifecycleOwner) {
            binding.homeCinemaName.text = it.name
        }

        viewModel.cinemaRooms.observe(viewLifecycleOwner) {
            recyclerView.adapter = CinemaRoomAdapter(it, viewModel)

            binding.homeCinemaEmpty.visibility = if (it.isEmpty()) VISIBLE else GONE
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Cinema not found, try again later", Toast.LENGTH_LONG).show()
        }

        viewModel.selectedCinemaRoom.observe(viewLifecycleOwner) {
            binding.homeRoomEdit.visibility = VISIBLE
            binding.homeMoviesTitle.visibility = VISIBLE
            binding.homeMovieAdd.visibility = VISIBLE

            viewModel.getRoomMoviesBy(it.id)
        }

        val moviesView = binding.homeMoviesList
        moviesView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        moviesView.adapter =
            MoviesAdapter(emptyMovieList(), multipleSelectionEnabled = false, showAllMovies = true)

        val screeningsView = binding.homeMovieScreeningList
        screeningsView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        screeningsView.adapter = ScreeningAdapter(emptyList())

        viewModel.selectedRoomMovies.observe(viewLifecycleOwner) {
            if (it.showing.isEmpty() && it.comingSoon.isEmpty()) {
                binding.homeMoviesList.visibility = GONE
                binding.homeCinemaEmpty.visibility = VISIBLE
                binding.homeCinemaEmptyText.setText(R.string.cinema_home_empty_movies)
                binding.homeCinemaEmptyButton.setText(R.string.cinema_home_empty_movies_button)
            } else {
                binding.homeCinemaEmpty.visibility = GONE

                getMovieListAdapter().updateData(it)

                getMovieListAdapter().selectedMovies.observe(viewLifecycleOwner) { selectedMovies ->
                    if (selectedMovies.isEmpty()) {
                        binding.homeMovieScreeningManager.visibility = GONE
                    } else {
                        if (viewModel.isSelectedAShowingMovie(selectedMovies[0])) {
                            binding.homeMovieScreeningManager.visibility = VISIBLE
                            viewModel.getScreenings(selectedMovies[0])
                        } else {
                            binding.homeMovieScreeningManager.visibility = GONE
                        }
                    }
                }

                binding.homeMoviesList.visibility = VISIBLE
            }
        }

        viewModel.selectedMovieScreenings.observe(viewLifecycleOwner) { screenings ->
            if (screenings.isEmpty()) {
                binding.homeMovieScreeningEmpty.visibility = VISIBLE
                binding.homeMovieScreeningList.visibility = GONE
            } else {
                binding.homeMovieScreeningEmpty.visibility = GONE
                binding.homeMovieScreeningList.visibility = VISIBLE

                getScreeningListAdapter().updateData(screenings)
            }
        }

        binding.homeMovieScreeningAdd.setOnClickListenerWithThrottle {
            if (viewModel.selectedMovieScreenings.isInitialized && viewModel.selectedMovieScreenings.value!!.isEmpty()) {
                return@setOnClickListenerWithThrottle
            }
            val singleItems = viewModel.selectedMovieScreenings.value!!.map {
                getScreeningTime(it)
            }.toTypedArray()
            val checkedItem = 0

            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.select_screening_time))
                .setNeutralButton(resources.getString(R.string.cancel)) { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton(resources.getString(R.string.next)) { dialog, which ->
                    showLanguageDialog(viewModel.selectedMovieScreenings.value!![which])
                    dialog.dismiss()
                }
                .setSingleChoiceItems(singleItems, checkedItem) { _, _ -> }
                .show()
        }
    }

    private fun showLanguageDialog(screening: Screening) {
        val singleItems = arrayOf(getString(R.string.dubbed), getString(R.string.subtitled))
        val checkedItem = 0

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.select_screening_time))
            .setNeutralButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(resources.getString(R.string.next)) { dialog, which ->
                val language = when (which) {
                    0 -> ScreeningFormat.DUBBED
                    else -> ScreeningFormat.SUBTITLED
                }
                viewModel.addScreeningFrom(
                    getMovieListAdapter().selectedMovies.value!![0],
                    screening,
                    language
                )
                dialog.dismiss()
            }
            .setSingleChoiceItems(singleItems, checkedItem) { _, _ -> }
            .show()
    }

    private var returningFromPause = false

    override fun onResume() {
        super.onResume()

        if (returningFromPause) {
            val cinemaId = arguments?.getInt(CINEMA_ID)
            cinemaId?.let { id ->
                viewModel.getCinemaBy(id)
                viewModel.getCinemaRoomsById(id)
            }
            returningFromPause = false
        }
    }

    override fun onPause() {
        super.onPause()

        returningFromPause = true
    }

    private fun getMovieListAdapter(): MoviesAdapter {
        return (binding.homeMoviesList.adapter as MoviesAdapter)
    }

    private fun getScreeningListAdapter(): ScreeningAdapter {
        return (binding.homeMovieScreeningList.adapter as ScreeningAdapter)
    }

    private fun to24HourFormat(date: Date): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(date)
    }

    private fun getScreeningTime(screening: Screening): String {
        return "${to24HourFormat(screening.startAt)} ${to24HourFormat(screening.endAt)}"
    }

    companion object {
        const val CINEMA_ID = "cinemaId"
        const val CINEMA_ROOM_ID = "cinemaRoomId"
    }
}