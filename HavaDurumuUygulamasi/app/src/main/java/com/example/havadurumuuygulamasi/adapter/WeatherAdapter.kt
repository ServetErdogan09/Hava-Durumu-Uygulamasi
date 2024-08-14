package com.example.havadurumuuygulamasi.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.havadurumuuygulamasi.R
import com.example.havadurumuuygulamasi.databinding.RecyclerTasarimBinding
import com.example.havadurumuuygulamasi.model.Forecast
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class WeatherAdapter(private val forecastList: List<Forecast>) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(val binding : RecyclerTasarimBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = RecyclerTasarimBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WeatherViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return forecastList.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val forecast  = forecastList[position]

        val (month, day) = parseDate(forecast.dt_txt)
        holder.binding.textViewSicaklik.text = "${forecast.main.temp}°"
        holder.binding.tarihTextView.text = "$day"
        holder.binding.textViewGunler.text = "$month"
        holder.binding.textViewHisedilen.text = "${forecast.main.feels_like}"
        havaDurumu(forecast.weather.first().description,holder)
    }

    private fun parseDate(dtTxt: String): Pair<String, String> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime = LocalDateTime.parse(dtTxt, formatter)

        val month = dateTime.month.getDisplayName(TextStyle.FULL, Locale("tr"))
        val day = dateTime.dayOfMonth.toString()
        val dayOfWeek  = dateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("tr"))


        return Pair(dayOfWeek, "$day $month")
    }

    private fun havaDurumu(havaDurumBilgisi: String, holder: WeatherViewHolder) {

        Log.e("havaDurumBilgisi",havaDurumBilgisi)

        when (havaDurumBilgisi.lowercase(Locale.ROOT)) {
            "few clouds", "cloudy","broken clouds","overcast clouds" -> holder.binding.havadurumuImage.setImageResource(R.drawable.cloudy) // Az bulutlu, Bulutlu
            "clear sky", "sunny" -> holder.binding.havadurumuImage.setImageResource(R.drawable.sunny) // Açık gökyüzü, Güneşli
            "rain", "shower rain" ,"heavy intensity rain"-> holder.binding.havadurumuImage.setImageResource(R.drawable.rainy) // Yağmur, Sağanak yağmur
            "snow", "snowy" -> holder.binding.havadurumuImage.setImageResource(R.drawable.snowy) // Kar, Karlı
            "storm", "thunderstorm" -> holder.binding.havadurumuImage.setImageResource(R.drawable.storm) // Fırtına, Gökgürültülü fırtına
            "wind", "windy" -> holder.binding.havadurumuImage.setImageResource(R.drawable.windy) // Rüzgar, Rüzgarlı
            else -> holder.binding.havadurumuImage.setImageResource(R.drawable.sunny) // Varsayılan bir görsel
        }
    }



}