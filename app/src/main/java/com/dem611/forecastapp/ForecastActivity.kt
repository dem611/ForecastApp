package com.dem611.forecastapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import com.dem611.forecastapp.data.OpenWeatherApiService
import kotlinx.android.synthetic.main.activity_forecast.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ForecastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)
        setSupportActionBar(toolbar)

        GlobalScope.launch {
            val response = OpenWeatherApiService()
                .getFutureWeatherAsync("Mesa")
                .await()
            weather_placeholder_textview.text = response.list[0].main.temp.toString()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.forecast_menu, menu)

        val searchMenuItem = menu.findItem(R.id.action_search)
        val searchView = searchMenuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@ForecastActivity, "Searched for: $query", Toast.LENGTH_LONG).show()

                supportActionBar?.title = query

                if (!searchView.isIconified)
                    searchView.isIconified = true

                searchMenuItem.collapseActionView()

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }
        )
        return true
    }
}