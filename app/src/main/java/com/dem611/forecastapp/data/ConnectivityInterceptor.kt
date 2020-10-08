package com.dem611.forecastapp.data

import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import com.dem611.forecastapp.UI.ForecastApplication
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.lang.Exception

class ConnectivityInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline())
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }

    private fun isOnline(): Boolean{
        val connectivityManager = ForecastApplication.context.getSystemService<ConnectivityManager>()

        val networkInfo = connectivityManager?.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected

    }

    class NoConnectivityException(): IOException(){
        override val message: String?
        get() = "No Internet Connection"

    }
}