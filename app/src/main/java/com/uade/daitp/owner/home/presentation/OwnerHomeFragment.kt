package com.uade.daitp.owner.home.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentOwnerHomeBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.owner.home.presentation.adapters.CinemaAdapter

class OwnerHomeFragment : Fragment(R.layout.fragment_owner_home) {

    private val viewModel = ViewModelDI.getOwnerHomeViewModel()
    private lateinit var binding: FragmentOwnerHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOwnerHomeBinding.bind(view)

        binding.homeCinemaAdd.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_ownerHomeFragment_to_ownerCinemaFormFragment)
        }

        val recyclerView = binding.homeCinemaList
        recyclerView.layoutManager = LinearLayoutManager(context)


        viewModel.cinemas.observe(viewLifecycleOwner) {
            recyclerView.adapter = CinemaAdapter(it, viewModel)
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.refresh()
    }

}