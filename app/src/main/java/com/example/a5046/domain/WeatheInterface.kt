package com.example.a5046.domain

import com.google.gson.JsonObject
import retrofit2.http.GET

interface WeatheInterface {
    @GET("v1/forecast?latitude=52.52&longitude=13.41&daily=weather_code,temperature_2m_max,temperature_2m_min&timezone=Australia%2FSydney")
    suspend fun getWeather(
    ): WeatherResponse

}
