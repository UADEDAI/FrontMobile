package com.uade.daitp.owner.register.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.owner.register.core.actions.RegisterOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OwnerRegisterViewModel(private val registerOwner: RegisterOwner) : ViewModel() {

    private val _registerSuccess: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val registerSuccess: LiveData<Boolean> get() = _registerSuccess

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    fun register(email: String, password: String, username: String, company: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                registerOwner(email, password, username, company)
                _registerSuccess.postValue(true)
            } catch (e: Exception) {
                _registerSuccess.postValue(false)
                _error.postValue(e.message)
            }
        }
    }
}