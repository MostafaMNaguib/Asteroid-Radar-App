package com.mostafa.udacity.asteroidradarapp.ui.main

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.mostafa.udacity.asteroidradarapp.data.model.Asteroid
import com.mostafa.udacity.asteroidradarapp.data.model.PictureOfDay
import com.mostafa.udacity.asteroidradarapp.data.repositories.AsteroidsRepository
import com.mostafa.udacity.asteroidradarapp.database.AsteroidDatabase
import com.mostafa.udacity.asteroidradarapp.network.checkInternetConnection
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel(app: Application): AndroidViewModel(app)
{

    private val database = AsteroidDatabase.getInstance(app)
    private val repository = AsteroidsRepository(database)
    val asteroids = repository.asteroids

    val todayAsteroids:LiveData<List<Asteroid>>
        get() = repository.todayAsteroids

    val asteroidsFromToday:LiveData<List<Asteroid>>
        get() = repository.asteroidsFromToday


    val pictureOfDay: LiveData<PictureOfDay>
        get() = repository.pictureOfDay

    private val _navigateToDetailFragment = MutableLiveData<Asteroid?>()
    val navigateToDetailFragment
        get() = _navigateToDetailFragment


    init {
        viewModelScope.launch {
            if (checkInternetConnection(app)) {
                viewModelScope.launch {
                    repository.refreshAsteroids()
                    repository.refreshPicture()
                }
            }else {
                Toast.makeText(app.applicationContext,"Connect to internet to get live data",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    fun onAsteroidItemClick(data: Asteroid) {
        _navigateToDetailFragment.value = data
    }

    fun onDetailFragmentNavigated() {
        _navigateToDetailFragment.value = null
    }

    private fun refreshAsteroids() {
        viewModelScope.launch {
            try {
                repository.refreshAsteroids()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



}