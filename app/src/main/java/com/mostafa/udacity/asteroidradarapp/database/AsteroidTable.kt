package com.mostafa.udacity.asteroidradarapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mostafa.udacity.asteroidradarapp.network.getEndDate
import com.mostafa.udacity.asteroidradarapp.utils.DatabaseConstants

@Entity(tableName = DatabaseConstants.TABLE_NAME)
data class AsteroidTable(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean,
    val day: String = getEndDate()
    )