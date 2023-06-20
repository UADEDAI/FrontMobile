package com.uade.daitp.module.factory

import com.squareup.moshi.Moshi
import com.uade.daitp.module.factory.adapter.BooleanAdapter
import com.uade.daitp.module.factory.adapter.DateAdapter
import com.uade.daitp.owner.home.core.repository.service.CinemaService
import com.uade.daitp.owner.home.core.repository.service.MovieService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object RetrofitFactory {

    val cinemaService: CinemaService by lazy { createRetrofitService().create(CinemaService::class.java) }
    val movieService: MovieService by lazy { createRetrofitService().create(MovieService::class.java) }

    private const val BASE_URL = "http://54.85.247.40/"

    private fun getMoshi() = Moshi.Builder()
        .add(BooleanAdapter())
        .add(DateAdapter())
        .build()

    private fun getHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    private fun createRetrofitService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(getMoshi()))
            .client(getHttpClient())
            .build()
    }
}