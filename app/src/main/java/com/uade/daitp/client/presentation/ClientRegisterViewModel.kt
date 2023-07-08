package com.uade.daitp.client.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.login.core.actions.GetUser
import com.uade.daitp.login.core.actions.UpdateUser
import com.uade.daitp.login.core.model.UserIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientRegisterViewModel(
    private val getUser: GetUser, private val updateUser: UpdateUser
) : ViewModel() {

    private val _registerSuccess: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val registerSuccess: LiveData<Boolean> get() = _registerSuccess

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    fun register(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val savedUser = getUser()
                updateUser(savedUser.id, UserIntent(name, ""))
                _registerSuccess.postValue(true)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
}