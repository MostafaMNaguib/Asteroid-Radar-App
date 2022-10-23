package com.mostafa.udacity.asteroidradarapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mostafa.udacity.asteroidradarapp.utils.DatabaseConstants

@Dao
interface DatabaseDao {

    @Query("SELECT * FROM ${DatabaseConstants.TABLE_NAME} ORDER by closeApproachDate")
    fun getAll(): LiveData<List<AsteroidTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(asteroids: List<AsteroidTable>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePictureOfDay(podTable: PictureOfDayTable)

    @Query("SELECT count(*) FROM ${DatabaseConstants.TABLE_NAME} WHERE date(:currentDay) = date(day)")
    fun checkExistedAsteroids(currentDay: String): Int

    @Query("SELECT * FROM ${DatabaseConstants.TABLE_NAME} ORDER BY closeApproachDate DESC")
    fun getAsteroids(): LiveData<List<AsteroidTable>>

    @Query("SELECT * FROM ${DatabaseConstants.TABLE_NAME} WHERE closeApproachDate =:currentDay")
    fun getTodayAsteroids(currentDay: String):LiveData<List<AsteroidTable>>

    @Query("SELECT * FROM ${DatabaseConstants.TABLE_NAME} WHERE closeApproachDate >=:currentDay ORDER BY closeApproachDate DESC")
    fun getAsteroidsFromToday(currentDay: String):LiveData<List<AsteroidTable>>

    @Query("SELECT count(*) FROM ${DatabaseConstants.POD_NAME} WHERE date(:currentDay) = date(day)")
    fun checkExistedPod(currentDay: String): Int

    @Query("SELECT * FROM ${DatabaseConstants.POD_NAME} ORDER BY day DESC LIMIT 1")
    fun getPictureOfDay(): LiveData<PictureOfDayTable>

    @Query("DELETE FROM ${DatabaseConstants.POD_NAME} WHERE date(:currentDay) > date(day)")
    fun deletePOD(currentDay: String)

    @Query("DELETE FROM ${DatabaseConstants.TABLE_NAME} WHERE date(:currentDay) > date(day)")
    fun deleteAsteroids(currentDay: String)

}