package com.chandra.github.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        private const val BASE_URL = "https://api.github.com/"
    }

    lateinit var retrotif: Retrofit

    fun getApiClient(): Retrofit {

            retrotif = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()

        return retrotif

    }
}