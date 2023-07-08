package com.uade.daitp.client.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.login.core.actions.DeleteUser
import com.uade.daitp.login.core.actions.LogoutUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientConfigurationViewModel(
    private val deleteUser: DeleteUser,
    private val logoutUser: LogoutUser
) : ViewModel() {

    private val _logoutSuccess: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val logoutSuccess: LiveData<Boolean> get() = _logoutSuccess

    private val _unsubscribeSuccess: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val unsubscribeSuccess: LiveData<Boolean> get() = _unsubscribeSuccess

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    fun logout() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                logoutUser()
                _logoutSuccess.postValue(true)
            } catch (e: Exception) {
                _logoutSuccess.postValue(false)
                _error.postValue(e.message)
            }
        }
    }

    fun unsubscribe() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                deleteUser()
                _unsubscribeSuccess.postValue(true)
            } catch (e: Exception) {
                _unsubscribeSuccess.postValue(false)
                _error.postValue(e.message)
            }
        }
    }
}