package com.uade.daitp.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.login.core.actions.LoginOwner

class LoginOwnerViewModel(private val loginOwner: LoginOwner) : ViewModel() {

    private val _userLoggedIn: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val userLoggedIn: LiveData<Boolean> get() = _userLoggedIn

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    fun login(username: String, password: String) {
        try {
            loginOwner(username, password)

            _userLoggedIn.value = true
        } catch (e: Exception) {
            _userLoggedIn.value = false
            _error.value = e.message
        }
    }
}