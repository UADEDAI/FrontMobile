package com.uade.daitp.client.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientRegisterViewModel() : ViewModel() {

    private val _registerSuccess: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val registerSuccess: LiveData<Boolean> get() = _registerSuccess

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    fun register(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                //TODO
                _registerSuccess.postValue(true)
            } catch (e: Exception) {
                _registerSuccess.postValue(false)
                _error.postValue(e.message)
            }
        }
    }
}