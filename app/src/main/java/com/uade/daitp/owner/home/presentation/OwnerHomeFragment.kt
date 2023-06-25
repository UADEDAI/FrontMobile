package com.uade.daitp.owner.home.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentOwnerHomeBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.owner.home.presentation.animations.ZoomOutPageTransformer

class OwnerHomeFragment : Fragment(R.layout.fragment_owner_home) {

    private val viewModel = ViewModelDI.getOwnerCinemasListViewModel()
    private lateinit var binding: FragmentOwnerHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOwnerHomeBinding.bind(view)

        val pagerAdapter = ScreenSlidePagerAdapter(requireActivity())
        binding.homePager.adapter = pagerAdapter
        binding.homePager.setPageTransformer(ZoomOutPageTransformer())

        binding.homePager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.homeNavigation.selectedItemId = R.id.home
                    1 -> binding.homeNavigation.selectedItemId = R.id.profile
                    2 -> binding.homeNavigation.selectedItemId = R.id.config
                }
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        binding.homeNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> binding.homePager.currentItem = 0
                R.id.profile -> binding.homePager.currentItem = 1
                R.id.config -> binding.homePager.currentItem = 2
            }
            return@setOnItemSelectedListener true
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = when (position) {
            0 ->
                OwnerCinemaListFragment(viewModel)
            1 ->
                OwnerProfileFragment()
            else ->
                OwnerConfigurationFragment()
        }
    }

    override fun onResume() {
        super.onResume()

        if (binding.homePager.currentItem == 0) {
            viewModel.refresh()
        }
    }

    private companion object {
        const val NUM_PAGES = 3
    }
}