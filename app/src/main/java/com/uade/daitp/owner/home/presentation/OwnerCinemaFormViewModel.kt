package com.uade.daitp.owner.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.login.core.actions.GetUser
import com.uade.daitp.owner.home.core.actions.AddCinema
import com.uade.daitp.owner.home.core.actions.GetCinema
import com.uade.daitp.owner.home.core.actions.UpdateCinema
import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.models.CreateCinemaIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OwnerCinemaFormViewModel(
    private val addCinema: AddCinema,
    private val updateCinema: UpdateCinema,
    private val getCinema: GetCinema,
    private val getUser: GetUser,
) : ViewModel() {

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    private val _processSuccess: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val processSuccess: LiveData<Boolean> get() = _processSuccess

    private val _cinemaToEdit: MutableLiveData<Cinema> by lazy { MutableLiveData<Cinema>() }
    val cinemaToEdit: LiveData<Cinema> get() = _cinemaToEdit

    fun processForm(
        createCinemaIntent: CreateCinemaIntent
    ) {
        _cinemaToEdit.value?.let {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    updateCinema(it.id, createCinemaIntent)
                    _processSuccess.postValue(true)
                } catch (e: Exception) {
                    _error.postValue(e.message)
                }
            }
        } ?: run {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    addCinema(createCinemaIntent)
                    _processSuccess.postValue(true)
                } catch (e: Exception) {
                    _error.postValue(e.message)
                }
            }
        }
    }

    fun getCinemaToEdit(cinemaId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cinema = getCinema(cinemaId)
                _cinemaToEdit.postValue(cinema)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    fun getUserId(): Int {
        return getUser().id
    }
}
