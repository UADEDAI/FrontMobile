package com.uade.daitp.owner.register.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.owner.register.core.actions.ValidateOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OwnerValidateViewModel(private val validateOwner: ValidateOwner) : ViewModel() {

    private val _validateSuccess: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val validateSuccess: LiveData<Boolean> get() = _validateSuccess

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    fun validate(code: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                validateOwner(code)
                _validateSuccess.postValue(true)
            } catch (e: Exception) {
                _validateSuccess.postValue(false)
                _error.postValue(e.message)
            }
        }
    }
}