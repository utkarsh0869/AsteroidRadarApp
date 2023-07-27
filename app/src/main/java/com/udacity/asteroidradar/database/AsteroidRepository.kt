package com.udacity.asteroidradar.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Network.NasaAsteroidApi
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AsteroidRepository(private val database: AsteroidDatabase) {

    private val startDate = getNextSevenDaysFormattedDates()[0]
    private val endDate = getNextSevenDaysFormattedDates()[6]
    private val apiKey = "FfQBqGjq408vfK0LKgFSvDKwiMuaHAHkgQuhTCnT"

    /**
     * Fetch the network result and store it in the local database.
     * Refresh the offline cache.
     */
    suspend fun refreshAsteroids() {
        try {
            val asteroids = NasaAsteroidApi.retrofitService.getAsteroidProperties(startDate, endDate, apiKey)

            val result = parseAsteroidsJsonResult(JSONObject(asteroids))
            withContext(Dispatchers.IO) {
                database.asteroidDao.insertAll(*result.asDatabaseModel())
            }

            Log.d("Refresh Asteroids", "Success")
        } catch (err: Exception) {
            Log.e("Failed: AsteroidRepFile", err.message.toString())
        }
    }

    /**
     * a list of asteroids that can be shown on the screen.
     * Load the videos from the offline cache.
     */
    val allAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroidsFromTodayOnwards(startDate)) {
            it.asDomainModel()
        }
}