package com.mostafa.udacity.asteroidradarapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mostafa.udacity.asteroidradarapp.data.model.Asteroid
import com.mostafa.udacity.asteroidradarapp.data.model.PictureOfDay
import com.mostafa.udacity.asteroidradarapp.database.*
import com.mostafa.udacity.asteroidradarapp.network.*
import com.mostafa.udacity.asteroidradarapp.network.Network.asteroidService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidsRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.databaseDao.getAll()) {
            it.asAsteroids()
        }

    //filter today
    val todayAsteroids:LiveData<List<Asteroid>> =
        Transformations.map(database.databaseDao.getTodayAsteroids(getEndDate())){ todayAsteroids ->
            todayAsteroids?.asAsteroids()
        }


    //filter saved
    val asteroidsFromToday:LiveData<List<Asteroid>> =
        Transformations.map(database.databaseDao.getAsteroidsFromToday(getEndDate())){ weekAsteroids ->
            weekAsteroids?.asAsteroids()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val todayAsteroidsInDb =
                database.databaseDao.checkExistedAsteroids(getEndDate())
            if (todayAsteroidsInDb == 0) {
                val response = asteroidService.getAsteroids(getStartDate(), getEndDate())
                if (response.isSuccessful) {
                    response.body()?.let {
                        val data = parseAsteroidsJsonResult(JSONObject(it))
                        database.databaseDao.insertAll(data.asAsteroidEntityList())
                    }
                }
            }

        }
    }

    suspend fun refreshPicture() {
        withContext(Dispatchers.IO) {
            val todayPODinDatabase = database.databaseDao.checkExistedPod(getEndDate())
            if (todayPODinDatabase == 0) {
                val response = asteroidService.getPictureOfDay()
                if (response.isSuccessful) {
                    response.body()?.let {
                        database.databaseDao.savePictureOfDay(it.asPODTable())
                    }
                }
            }
        }
    }

    val pictureOfDay: LiveData<PictureOfDay> =
        Transformations.map(database.databaseDao.getPictureOfDay()) {podTable ->
            podTable?.asPODModel()
        }
}