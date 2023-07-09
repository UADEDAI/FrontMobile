package com.uade.daitp.client.core.actions

import com.uade.daitp.client.core.model.ScreeningClient
import com.uade.daitp.client.core.repository.ClientRepository
import java.util.*

class GetScreeningsClientBy(private val clientRepository: ClientRepository) {

    suspend operator fun invoke(cinemaId: Int, movieId: Int, date: Date): List<ScreeningClient> {
        return clientRepository.getScreeningsBy(cinemaId, movieId, date)
    }

}