package com.uade.daitp.module.factory

import com.squareup.moshi.Moshi
import com.uade.daitp.module.factory.adapter.BooleanAdapter
import com.uade.daitp.owner.home.core.repository.service.CinemaService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitFactory {

    fun getCinemaService(): CinemaService {
        return createRetrofitService().create(CinemaService::class.java)
    }

    private const val BASE_URL = "http://54.85.247.40/"

    private fun getMoshi() = Moshi.Builder()
        .add(BooleanAdapter())
        .build()

    private fun createRetrofitService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(getMoshi()))
            .build()
    }
}