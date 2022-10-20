package com.mostafa.udacity.asteroidradarapp.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.mostafa.udacity.asteroidradarapp.data.model.Asteroid
import com.mostafa.udacity.asteroidradarapp.data.model.PictureOfDay
import com.mostafa.udacity.asteroidradarapp.data.repositories.AsteroidsRepository
import com.mostafa.udacity.asteroidradarapp.database.AsteroidDatabase
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel(app: Application): AndroidViewModel(app)
{

    private val database = AsteroidDatabase.getInstance(app)
    private val repository = AsteroidsRepository(database)
    val asteroids = repository.asteroids

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _navigateToDetailFragment = MutableLiveData<Asteroid?>()
    val navigateToDetailFragment
        get() = _navigateToDetailFragment

    private val mockData = false
    private val _mockAsteroids = MutableLiveData<List<Asteroid>>()
    val mockAsteroids : LiveData<List<Asteroid>>
        get() = _mockAsteroids

    init {
        if(mockData) {
            mockData()
        } else {
            refreshAsteroids()
            getPictureOfDay()
        }
    }

    private fun mockData() {

        val dataList = mutableListOf<Asteroid>()

        var count = 1
        while (count <= 10) {

            val data = Asteroid(
                count.toLong(),
                "codename:$count",
                "2022-10-$count",
                Random(100).nextDouble(),
                Random(100).nextDouble(),
                Random(100).nextDouble(),
                Random(100).nextDouble(),
                true)

            dataList.add(data)

            ++count
        }

        _mockAsteroids.postValue(dataList)
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

    private fun getPictureOfDay() {
        viewModelScope.launch {
            try {
                _pictureOfDay.value = repository.getPictureOfDay()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}