package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroidEntity: AsteroidEntity)

    //Today
    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate = :startDate ORDER BY closeApproachDate DESC")
    fun getAsteroidsDay(startDate: String): LiveData<List<AsteroidEntity>>

    //All asteroids
    @Query("SELECT * FROM asteroid_table ORDER BY closeApproachDate DESC")
    fun getAsteroids(): LiveData<List<AsteroidEntity>>

    //Weekly
    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate BETWEEN :startDate AND :endDate ORDER BY closeApproachDate DESC")
    fun getAsteroidsDate(startDate: String, endDate: String): LiveData<List<AsteroidEntity>>
}
