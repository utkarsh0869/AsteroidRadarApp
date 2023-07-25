package com.udacity.asteroidradar.main

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Network.NasaAsteroidApi
import com.udacity.asteroidradar.Network.NasaAsteroidApiService
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        getNasaAsteroidProperties()
    }

    private fun getNasaAsteroidProperties() {

        val startDate = getNextSevenDaysFormattedDates()[0]
        val endDate = getNextSevenDaysFormattedDates()[6]
        val apiKey = "FfQBqGjq408vfK0LKgFSvDKwiMuaHAHkgQuhTCnT"

        NasaAsteroidApi.retrofitService.getAsteroidProperties(startDate, endDate, apiKey)
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val jsonObject = JSONObject(response.body())

                        jsonObject.let {
                            _response.value = "Response Length: ${jsonObject.toString().length}"
                            Log.d("HERE", jsonObject.toString())
                            val asd = parseAsteroidsJsonResult(jsonObject)
                            _response.value = asd[0].id.toString()
                        }
                    } else {
                        _response.value = "Failure: ${response.errorBody()?.string()}"
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    _response.value = "Failure: ${t.message}"
                }
            })

    }
}
