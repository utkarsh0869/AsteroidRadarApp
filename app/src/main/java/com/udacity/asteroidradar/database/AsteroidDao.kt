import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.udacity.asteroidradar.database.AsteroidEntity

@Dao
interface AsteroidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroid(asteroid: AsteroidEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroids(asteroids: List<AsteroidEntity>)
}
