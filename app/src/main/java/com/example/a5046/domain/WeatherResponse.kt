package com.example.a5046.domain

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherResponse(

    @SerializedName("timezone")
    val timezone: String = "",
//    val timezone: String = "",
    @SerializedName("daily")
    val daily: JsonObject = JsonObject(),
)
