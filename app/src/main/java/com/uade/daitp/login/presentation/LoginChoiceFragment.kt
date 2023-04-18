package com.uade.daitp.login.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentLoginChoiceBinding

class LoginChoiceFragment : Fragment(R.layout.fragment_login_choice) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginChoiceBinding.bind(view)

        binding.loginEmail.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginChoiceFragment_to_loginEmailFragment)
        }

        binding.loginGoogle.setOnClickListener {

        }
    }
}