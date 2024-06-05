package com.dicoding.tanaminai.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dicoding.tanaminai.data.weather.WeatherApiConfig
import com.dicoding.tanaminai.data.weather.WeatherApiService
import com.dicoding.tanaminai.data.weather.WeatherResponse
import com.dicoding.tanaminai.utils.ResultState

class HomeViewModel : ViewModel() {

    private val weatherApiService: WeatherApiService = WeatherApiConfig.getWeatherApiService()

    fun getCurrentWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): LiveData<ResultState<WeatherResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = weatherApiService.getCurrentWeather(lat, lon, apiKey)
            emit(ResultState.Success(response))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

}