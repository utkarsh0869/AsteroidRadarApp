package com.udacity.asteroidradar.Network

import com.udacity.asteroidradar.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .build()


interface NasaAsteroidApiService {
    @GET("neo/rest/v1/feed")
    fun getAsteroidProperties(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): Call<String>
}

object NasaAsteroidApi {
    val retrofitService: NasaAsteroidApiService by lazy {
        retrofit.create(NasaAsteroidApiService::class.java)
    }
}
