package com.example.havadurumuuygulamasi.service

import com.example.havadurumuuygulamasi.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// servisimiz apı istekleri bu servisle atılacak
interface WeatherService {
    @GET("forecast")
    fun getWeatherForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): Call<WeatherResponse>
}