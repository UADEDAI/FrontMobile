package com.uade.daitp.owner.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.owner.home.core.actions.AddCinemaRoom
import com.uade.daitp.owner.home.core.actions.GetCinemaRoom
import com.uade.daitp.owner.home.core.actions.UpdateCinemaRoom
import com.uade.daitp.owner.home.core.models.CinemaRoom
import com.uade.daitp.owner.home.core.models.CreateCinemaRoomIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OwnerCinemaRoomFormViewModel(
    private val getCinemaRoom: GetCinemaRoom,
    private val addCinemaRoom: AddCinemaRoom,
    private val updateCinemaRoom: UpdateCinemaRoom
) : ViewModel() {

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    private val _processSuccess: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val processSuccess: LiveData<Boolean> get() = _processSuccess

    private val _roomToEdit: MutableLiveData<CinemaRoom> by lazy { MutableLiveData<CinemaRoom>() }
    val roomToEdit: LiveData<CinemaRoom> get() = _roomToEdit

    fun processForm(createIntent: CreateCinemaRoomIntent) {
        _roomToEdit.value?.let {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    updateCinemaRoom(it.id, createIntent)
                    _processSuccess.postValue(true)
                } catch (e: Exception) {
                    _error.postValue(e.message)
                }
            }
        } ?: run {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    addCinemaRoom(createIntent)
                    _processSuccess.postValue(true)
                } catch (e: Exception) {
                    _error.postValue(e.message)
                }
            }
        }
    }

    fun getRoom(roomId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _roomToEdit.postValue(getCinemaRoom(roomId))
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
}
