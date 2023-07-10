package com.uade.daitp.client.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.login.core.actions.GetUser
import com.uade.daitp.login.core.actions.UpdateUser
import com.uade.daitp.login.core.model.User
import com.uade.daitp.login.core.model.UserIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientProfileViewModel(
    private val getUser: GetUser,
    private val updateUser: UpdateUser
) : ViewModel() {

    private val _user: MutableLiveData<User> by lazy { MutableLiveData<User>() }
    val user: LiveData<User> get() = _user

    private val _profileUpdated: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val profileUpdated: LiveData<Boolean> get() = _profileUpdated

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    init {
        refreshUser()
    }

    fun update(name: String, avatar: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = _user.value!!
                updateUser(user.id, UserIntent(name, "", avatar))
                _profileUpdated.postValue(true)
                refreshUser()
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    private fun refreshUser() {
        val savedUser = getUser()
        _user.postValue(savedUser)
    }
}