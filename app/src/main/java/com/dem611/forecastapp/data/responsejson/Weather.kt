package com.dem611.forecastapp.data.responsejson


import com.google.gson.annotations.SerializedName

data class Weather(
    val description: String,
    val icon: String
)