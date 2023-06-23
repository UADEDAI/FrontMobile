package com.uade.daitp.owner.recovery.presentacion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.owner.recovery.core.actions.RecoverPassword
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OwnerRecoverPasswordViewModel(private val recoverPassword: RecoverPassword) : ViewModel() {

    private val _recoverSuccess: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val recoverSuccess: LiveData<Boolean> get() = _recoverSuccess

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    fun recover(code: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                recoverPassword(code, password)
                _recoverSuccess.postValue(true)
            } catch (e: Exception) {
                _recoverSuccess.postValue(false)
                _error.postValue(e.message)
            }
        }
    }
}