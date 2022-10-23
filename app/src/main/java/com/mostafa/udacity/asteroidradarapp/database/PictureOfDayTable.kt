package com.mostafa.udacity.asteroidradarapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mostafa.udacity.asteroidradarapp.network.getEndDate
import com.mostafa.udacity.asteroidradarapp.utils.Constants
import com.mostafa.udacity.asteroidradarapp.utils.DatabaseConstants.POD_NAME

@Entity(tableName = POD_NAME)
data class PictureOfDayTable(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val url: String,
    val mediaType: String,
    val title: String,
    val day: String = getEndDate()
)
