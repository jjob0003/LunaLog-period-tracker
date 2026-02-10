package com.example.a5046.domain

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherObject {

    val weatherService: WeatheInterface by lazy{
        Log.i("weatherController", "called")
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(WeatheInterface::class.java)
    }

}