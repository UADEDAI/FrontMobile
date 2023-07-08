package com.uade.daitp.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.login.core.actions.LoginClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginGoogleViewModel(private val loginClient: LoginClient) : ViewModel() {

    private val _userLoggedIn: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val userLoggedIn: LiveData<Boolean> get() = _userLoggedIn

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    fun loginGoogle(idToken: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val shouldDefineName = loginClient.invoke(idToken)
                _userLoggedIn.postValue(shouldDefineName)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
}