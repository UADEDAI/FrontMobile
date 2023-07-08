package com.uade.daitp.client.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentClientHomeBinding
import com.uade.daitp.owner.home.presentation.animations.ZoomOutPageTransformer

class ClientHomeFragment : Fragment(R.layout.fragment_client_home) {

    private lateinit var binding: FragmentClientHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentClientHomeBinding.bind(view)

        val pagerAdapter = ScreenSlidePagerAdapter(requireActivity())
        binding.homePager.adapter = pagerAdapter
        binding.homePager.setPageTransformer(ZoomOutPageTransformer())

        binding.homePager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.homeNavigation.selectedItemId = R.id.reservation
                    1 -> binding.homeNavigation.selectedItemId = R.id.home
                    2 -> binding.homeNavigation.selectedItemId = R.id.profile
                    3 -> binding.homeNavigation.selectedItemId = R.id.config
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
                R.id.reservation -> binding.homePager.currentItem = 0
                R.id.home -> binding.homePager.currentItem = 1
                R.id.profile -> binding.homePager.currentItem = 2
                R.id.config -> binding.homePager.currentItem = 3
            }
            return@setOnItemSelectedListener true
        }

        binding.homePager.post {
            binding.homePager.setCurrentItem(1, true)
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = when (position) {
            0 ->
                ClientHomeReservationsFragment()
            1 ->
                ClientHomeMoviesListFragment()
            2 ->
                ClientProfileFragment()
            else ->
                ClientConfigurationFragment()
        }
    }

    private companion object {
        const val NUM_PAGES = 4
    }
}