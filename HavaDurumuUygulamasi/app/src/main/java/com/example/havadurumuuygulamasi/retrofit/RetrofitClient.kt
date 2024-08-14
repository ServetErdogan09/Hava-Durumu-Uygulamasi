package com.example.havadurumuuygulamasi.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    val retrofitInstance : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // json verilerini kotlin nesnelerine dönüştürecek
            .build()
    }
}