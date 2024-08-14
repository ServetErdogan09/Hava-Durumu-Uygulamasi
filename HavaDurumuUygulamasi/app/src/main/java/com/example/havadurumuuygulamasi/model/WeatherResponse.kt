package com.example.havadurumuuygulamasi.model


// json verileri olarak gelecek verileri temsl edecek model sınıflar
data class WeatherResponse(
    val city: City,// Şehir bilgileri
    val list: List<Forecast>// Hava durumu tahminleri listesi
)

data class Forecast(
    val dt: Long, // Unix zaman damgası (tarih ve saat)
    val main: Main, // Ana hava durumu bilgileri
    val weather: List<Weather>, // Hava durumu açıklamaları
    val wind: Wind, // Rüzgar bilgileri
    val dt_txt: String // Tarih ve saat bilgisi (örneğin "2024-08-14 09:00:00")
)

data class Main(
    val temp: Float, // Hava sıcaklığı
    val temp_min: Float, // Minimum sıcaklık
    val temp_max: Float, // Maksimum sıcaklık
    val humidity: Int, // Nem oranı (%)
    val pressure: Int, // Hava basıncı (hPa)
    val feels_like: Float // Hissedilen sıcaklık (gerçek hissedilen sıcaklık)
)


data class Weather(
    val description: String, // Hava durumu açıklaması (örneğin "az bulutlu")
    val main : String // Ana hava durumu (örneğin "bulutlu")
)

data class Wind(
    val speed: Float // Rüzgar hızı (m/s)
)

data class City(
    val name: String // Şehir adı
)
