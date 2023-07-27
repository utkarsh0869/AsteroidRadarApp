package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM asteroid_table")
    fun getAsteroidEntity(): LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroidEntity: AsteroidEntity)

    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate >= :todayDate ORDER BY closeApproachDate ASC")
    fun getAsteroidsFromTodayOnwards(todayDate: String): LiveData<List<AsteroidEntity>>
}
