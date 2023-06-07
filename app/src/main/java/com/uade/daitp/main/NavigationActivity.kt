package com.uade.daitp.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentActivity
import com.uade.daitp.databinding.ActivityNavigationBinding

class NavigationActivity : FragmentActivity() {

    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}
