package com.mostafa.udacity.asteroidradarapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mostafa.udacity.asteroidradarapp.data.model.Asteroid
import com.mostafa.udacity.asteroidradarapp.data.model.PictureOfDay
import com.mostafa.udacity.asteroidradarapp.database.AsteroidDatabase
import com.mostafa.udacity.asteroidradarapp.database.asAsteroids
import com.mostafa.udacity.asteroidradarapp.network.NetworkBuilder
import com.mostafa.udacity.asteroidradarapp.network.asAsteroidEntries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidsRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.databaseDao.getAll()) {
            it.asAsteroids()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroids = NetworkBuilder.getAsteroids()
            database.databaseDao.insertAll(asteroids.asAsteroidEntries())
        }
    }

    suspend fun getPictureOfDay(): PictureOfDay {
        lateinit var pictureOfDay: PictureOfDay
        withContext(Dispatchers.IO) {
            pictureOfDay = NetworkBuilder.getPictureOfDay()
        }
        return pictureOfDay
    }
}