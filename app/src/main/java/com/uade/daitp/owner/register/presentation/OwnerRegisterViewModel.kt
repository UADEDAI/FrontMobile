package com.uade.daitp.owner.register.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.owner.register.core.actions.RegisterOwner

class OwnerRegisterViewModel(private val registerOwner: RegisterOwner) : ViewModel() {

    private val _registerSuccess: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val registerSuccess: LiveData<Boolean> get() = _registerSuccess

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    fun register(email: String, password: String, username: String, company: String) {
        try {
            registerOwner(email, password, username, company)
            _registerSuccess.value = true
        } catch (e: Exception) {
            _registerSuccess.value = false
            _error.value = e.message
        }
    }
}