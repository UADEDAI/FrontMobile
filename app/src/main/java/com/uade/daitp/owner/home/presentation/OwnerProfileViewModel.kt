package com.uade.daitp.owner.home.presentation

import android.util.Log
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

class OwnerProfileViewModel(
    private val getUser: GetUser,
    private val updateUser: UpdateUser
) : ViewModel() {

    private val _user: MutableLiveData<User> by lazy { MutableLiveData<User>() }
    val user: LiveData<User> get() = _user

    init {
        refreshUser()
    }

    fun update(name: String, company: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = _user.value!!
                updateUser(user.id, UserIntent(name, company))
                refreshUser()
            } catch (e: Exception) {
                Log.d("OwnerProfileViewModel", "no saved user or expired token")
            }
        }
    }

    private fun refreshUser() {
        val savedUser = getUser()
        _user.postValue(savedUser)
    }
}