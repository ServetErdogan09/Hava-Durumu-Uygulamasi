package com.example.havadurumuuygulamasi

import com.example.havadurumuuygulamasi.model.City
import com.example.havadurumuuygulamasi.model.Forecast
import com.example.havadurumuuygulamasi.model.Main
import com.example.havadurumuuygulamasi.model.Weather
import com.example.havadurumuuygulamasi.model.WeatherResponse
import com.example.havadurumuuygulamasi.model.Wind
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class WeatherResponseTest {

    @Test
    fun testWeatherResponseCreation() {
        val weather = Weather("az bulutlu", "bulutlu")
        val main = Main(25.5f, 20.0f, 30.0f, 60, 1012, 26.0f)
        val wind = Wind(5.5f)
        val forecast = Forecast(1629892800L, main, listOf(weather), wind, "2024-08-14 09:00:00")
        val city = City("Istanbul")

        val response = WeatherResponse(city, listOf(forecast))

        assertNotNull(response)
        assertEquals("Istanbul", response.city.name)
        assertEquals(1, response.list.size)
        
        val firstForecast = response.list[0]
        assertEquals(25.5f, firstForecast.main.temp, 0.0f)
        assertEquals("az bulutlu", firstForecast.weather[0].description)
        assertEquals(5.5f, firstForecast.wind.speed, 0.0f)
        assertEquals("2024-08-14 09:00:00", firstForecast.dt_txt)
    }
}
