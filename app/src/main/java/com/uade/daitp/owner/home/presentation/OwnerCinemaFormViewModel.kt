package com.uade.daitp.owner.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uade.daitp.owner.home.core.actions.AddCinema
import com.uade.daitp.owner.home.core.actions.DeleteCinema
import com.uade.daitp.owner.home.core.actions.GetCinema
import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.owner.home.core.models.CreateCinemaIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OwnerCinemaFormViewModel(
    private val addCinema: AddCinema,
    private val deleteCinema: DeleteCinema,
    private val getCinema: GetCinema
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
        try {
            _cinemaToEdit.value?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    deleteCinema(it.id)
                    addCinema(createCinemaIntent)
                    _processSuccess.postValue(true)
                }
            }
        } catch (e: Exception) {
            _error.postValue(e.message)
        }
    }

    fun getCinemaToEdit(cinemaId: Int) {
        try {
            _cinemaToEdit.value?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    val cinema = getCinema(cinemaId)
                    _cinemaToEdit.postValue(cinema)
                }
            }
        } catch (e: Exception) {
            _error.postValue(e.message)
        }
    }

    fun getUserId(): Int {
        return 0

        //TODO get saved User Id
    }
}
