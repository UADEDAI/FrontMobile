package com.uade.daitp.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.login.core.actions.LoginOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginOwnerViewModel(private val loginOwner: LoginOwner) : ViewModel() {

    private val _userLoggedIn: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val userLoggedIn: LiveData<Boolean> get() = _userLoggedIn

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    fun login(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                loginOwner(username, password)

                _userLoggedIn.postValue(true)
            } catch (e: Exception) {
                _userLoggedIn.postValue(false)
                _error.postValue(e.message)
            }
        }
    }
}