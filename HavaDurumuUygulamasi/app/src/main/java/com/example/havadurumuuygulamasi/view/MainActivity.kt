package com.example.havadurumuuygulamasi.view

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.havadurumuuygulamasi.R
import com.example.havadurumuuygulamasi.adapter.WeatherAdapter
import com.example.havadurumuuygulamasi.databinding.ActivityMainBinding
import com.example.havadurumuuygulamasi.model.Forecast
import com.example.havadurumuuygulamasi.model.WeatherResponse
import com.example.havadurumuuygulamasi.retrofit.RetrofitClient
import com.example.havadurumuuygulamasi.service.WeatherService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var weatherService: WeatherService
    private lateinit var requestLocationPermission: ActivityResultLauncher<String>
    private lateinit var flap: FusedLocationProviderClient
    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherAdapter: WeatherAdapter
    private lateinit var locationRequest: LocationRequest
    private var locationCallback: LocationCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // FusedLocationProviderClient'i başlat
        flap = LocationServices.getFusedLocationProviderClient(this)

        // Retrofit ve WeatherService'i başlat
        weatherService = RetrofitClient.retrofitInstance.create(WeatherService::class.java)


        // İzin isteme başlatıcısını başlat
        requestLocationPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startLocationUpdates()
            } else {
                // İzin reddedildi, gerekirse kullanıcıya bilgi ver
                Toast.makeText(this, "Konum izni verilmedi.", Toast.LENGTH_SHORT).show()
            }
        }

        // İzinleri kontrol et ve gerekirse iste
        checkAndRequestPermissions()
    }


    private fun checkAndRequestPermissions() {
        val userPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        if (userPermission != PackageManager.PERMISSION_GRANTED) {
            // İzin verilmemişse, izni iste
            requestLocationPermission.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            // İzin zaten verilmişse, konum güncellemelerini başlat
            startLocationUpdates()
        }


    }

    // 10 saniyede bir konum güncellemesi ve en hızlı 5 saniyede bir güncelleme yap
    private fun startLocationUpdates() {
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setMinUpdateIntervalMillis(5000)
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.let {
                    val location: Location? = it.lastLocation
                    val latitude = location?.latitude
                    val longitude = location?.longitude
                    Log.e("konum", "latitude : $latitude")
                    Log.e("konum", "longitude : $longitude")

                    if (latitude != null && longitude != null) {
                        havaDurumuIstek(latitude, longitude)
                    }
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        locationCallback?.let {
            flap.requestLocationUpdates(locationRequest, it, null)
        }
    }



    private fun havaDurumuIstek(lat: Double, lon: Double) {
        weatherService.getWeatherForecast(lat, lon, "70b8722418eaa4d23485a2857eaec7e1").enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                // veri geldi mi
                if (response.isSuccessful) {
                    val weatherResponse = response.body()

                    weatherResponse?.let { weather ->
                        val forecastList = filterMorningForecasts(weather.list)
                        for (i in forecastList) {
                            binding.tempTextView.text = "${forecastList.first().main.temp}°"
                            binding.textView2.text = forecastList.first().dt_txt
                            binding.textViewDescription.text = forecastList.first().weather.first().description
                            binding.feelLikeText.text = "${forecastList.first().main.feels_like}°C"
                            binding.textViewSehir.text = weatherResponse.city.name
                            binding.windTextView.text = "${forecastList.first().wind.speed} m/s"
                            binding.pressureTextView.text = "${forecastList.first().main.pressure} hPa"
                            binding.minTempText.text = "Min Temp: ${forecastList.first().main.temp_min}"
                            binding.maxTempText.text = "Max Temp: ${forecastList.first().main.temp_max}"



                            val remainingForecasts = forecastList.drop(1)
                            setupRecyclerView(remainingForecasts)
                        }
                        //havaDurumu(forecastList.first().weather.first().description)
                        Log.e("havaDurumBilgisi1", forecastList.first().weather.first().description)
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Hava durumu verisi alınamadı.", Toast.LENGTH_SHORT).show()
                }
            }

            // veri gelirken hata oluşursa
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Hava durumu verisi alınamadı.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // adapter ile listeyi ve kodları birbirine bağlamak
    private fun setupRecyclerView(forecastList: List<Forecast>) {
        weatherAdapter = WeatherAdapter(forecastList)
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)
        binding.RecyclerView.adapter = weatherAdapter
    }

    // hava durumundan gelen verileri filtrelemek
    private fun filterMorningForecasts(forecastList: List<Forecast>): List<Forecast> {
        return forecastList.filter { forecast ->
            val dateTime = LocalDateTime.parse(forecast.dt_txt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            dateTime.hour == 9
        }
    }


    // konum güncellemelerini durdur
    override fun onPause() {
        super.onPause()
        locationCallback?.let {
            flap.removeLocationUpdates(it)
        }
    }


}
