package com.uade.daitp.module.factory

import com.squareup.moshi.Moshi
import com.uade.daitp.login.core.repository.LoginService
import com.uade.daitp.login.infrastructure.repository.SharedPrefUserRepository
import com.uade.daitp.login.infrastructure.repository.UserRepository
import com.uade.daitp.module.factory.adapter.BooleanAdapter
import com.uade.daitp.module.factory.adapter.DateAdapter
import com.uade.daitp.owner.home.core.repository.service.CinemaService
import com.uade.daitp.owner.home.core.repository.service.MovieService
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitFactory {

    val cinemaService: CinemaService by lazy { createRetrofitService(getHttpClient()).create(CinemaService::class.java) }
    val movieService: MovieService by lazy { createRetrofitService(getAuthorizedHttpClient()).create(MovieService::class.java) }
    val loginService: LoginService by lazy { createRetrofitService(getAuthorizedHttpClient()).create(LoginService::class.java) }

    private const val BASE_URL = "http://54.85.247.40/"
    private val userRepository: UserRepository = SharedPrefUserRepository()

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

    private fun getAuthorizedHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor {
                val request = it.request().newBuilder()
                    .addHeader("Authorization", userRepository.getBearerToken())
                    .build()
                return@addInterceptor it.proceed(request)
            }
            .build()
    }

    private fun createRetrofitService(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(getMoshi()))
            .client(client)
            .build()
    }
}