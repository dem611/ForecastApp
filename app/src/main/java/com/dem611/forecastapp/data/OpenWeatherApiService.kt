package com.dem611.forecastapp.data

import com.dem611.forecastapp.data.responsejson.ForecastResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "d2a381b8abe18ae65dfd1c7a84b7ee6a"
const val UNITS_SYSTEM = "imperial"

interface OpenWeatherApiService {

    @GET("forecast")
    fun getFutureWeatherAsync(
        @Query("q") cityAndCountryCode: String
    ): Deferred<ForecastResponse>

    companion object {
        operator fun invoke(): OpenWeatherApiService{

            val apiKeyInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("appid", API_KEY)
                    .addQueryParameter("units", UNITS_SYSTEM)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)

            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .addInterceptor(ConnectivityInterceptor())
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherApiService::class.java)
        }
    }


}