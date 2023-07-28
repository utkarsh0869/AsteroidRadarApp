package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.FilterAsteroid
import com.udacity.asteroidradar.Network.NasaAsteroidApi
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.AsteroidDatabase.Companion.getDatabase
import com.udacity.asteroidradar.database.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    init {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
            refreshPictureOfDay()
        }
    }

    private var _filterAsteroid = MutableLiveData(FilterAsteroid.ALL)

    val asteroidList = Transformations.switchMap(_filterAsteroid) {
        when (it!!) {
            FilterAsteroid.WEEK -> asteroidRepository.weekAsteroids
            FilterAsteroid.TODAY -> asteroidRepository.todayAsteroids
            else -> asteroidRepository.allAsteroids
        }
    }

    fun onChangeFilter(filter: FilterAsteroid) {
        _filterAsteroid.postValue(filter)
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }

    private suspend fun refreshPictureOfDay() {
        withContext(Dispatchers.IO) {
            try {
                val api_key = "FfQBqGjq408vfK0LKgFSvDKwiMuaHAHkgQuhTCnT"
                val pictureOfDay = NasaAsteroidApi.retrofitService.getPictureOfTheDay(api_key)

                // Check the media type of the PictureOfDay
                if (pictureOfDay.mediaType == "image") {
                    // Download and use the image
                    _pictureOfDay.postValue(pictureOfDay)
                } else {
                    // Ignore videos (and other media types)
                }

            } catch (err: Exception) {
                Log.e("refreshPictureOfDay", err.printStackTrace().toString())
            }
        }
    }

}

