package com.uade.daitp.owner.home.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentOwnerCinemaListBinding
import com.uade.daitp.owner.home.presentation.adapters.CinemaAdapter
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class OwnerCinemaListFragment(private val viewModel: OwnerCinemasListViewModel) :
    Fragment(R.layout.fragment_owner_cinema_list) {

    private lateinit var binding: FragmentOwnerCinemaListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOwnerCinemaListBinding.bind(view)

        binding.homeCinemaAdd.setOnClickListenerWithThrottle {
            view.findNavController()
                .navigate(R.id.action_ownerHomeFragment_to_ownerCinemaFormFragment)
        }

        binding.homeCinemaEmptyButton.setOnClickListenerWithThrottle {
            view.findNavController()
                .navigate(R.id.action_ownerHomeFragment_to_ownerCinemaFormFragment)
        }

        val recyclerView = binding.homeCinemaList
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.cinemas.observe(viewLifecycleOwner) {
            recyclerView.adapter = CinemaAdapter(it, viewModel)

            binding.homeCinemaEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.refresh()
    }

}