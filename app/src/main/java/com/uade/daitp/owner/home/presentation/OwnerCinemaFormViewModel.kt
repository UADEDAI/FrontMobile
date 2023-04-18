package com.uade.daitp.owner.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.owner.home.core.actions.AddCinemas
import com.uade.daitp.owner.home.core.actions.DeleteCinema
import com.uade.daitp.owner.home.core.models.CreateCinemaIntent

class OwnerCinemaFormViewModel(
    private val addCinema: AddCinemas,
    private val deleteCinema: DeleteCinema
) : ViewModel() {

    private val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    private val _processSuccess: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val processSuccess: LiveData<Boolean> get() = _processSuccess

    fun processForm(
        previousCinemaId: Int?,
        createCinemaIntent: CreateCinemaIntent
    ) {
        try {
            addCinema(createCinemaIntent)
            previousCinemaId?.let {
                deleteCinema(previousCinemaId)
            }
            _processSuccess.postValue(true)
        } catch (e: Exception) {
            _error.postValue(e.message)
        }
    }
}
