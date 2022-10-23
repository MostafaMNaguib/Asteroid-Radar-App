package com.mostafa.udacity.asteroidradarapp.database

import com.mostafa.udacity.asteroidradarapp.data.model.Asteroid
import com.mostafa.udacity.asteroidradarapp.data.model.PictureOfDay


fun List<AsteroidTable>.asAsteroids() : List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}

fun AsteroidTable.asAsteroidModel():Asteroid{
    return Asteroid(
        id = id,
        codename = codename,
        closeApproachDate=closeApproachDate,
        absoluteMagnitude = absoluteMagnitude,
        estimatedDiameter = estimatedDiameter,
        relativeVelocity = relativeVelocity,
        distanceFromEarth = distanceFromEarth,
        isPotentiallyHazardous = isPotentiallyHazardous
    )
}

fun List<Asteroid>.asAsteroidEntityList(): List<AsteroidTable>{
    return map { asteroid ->
        AsteroidTable(
            id = asteroid.id,
            codename = asteroid.codename,
            absoluteMagnitude = asteroid.absoluteMagnitude,
            estimatedDiameter = asteroid.estimatedDiameter,
            isPotentiallyHazardous = asteroid.isPotentiallyHazardous,
            closeApproachDate = asteroid.closeApproachDate,
            relativeVelocity = asteroid.relativeVelocity,
            distanceFromEarth = asteroid.distanceFromEarth
        )}.toList()

}

fun List<AsteroidTable>.asAsteroidList():List<Asteroid>{
    return map { asteroidEntity ->
        asteroidEntity.asAsteroidModel()
    }
}

fun PictureOfDay.asPODTable():PictureOfDayTable{
    return PictureOfDayTable(
        url = url,
        title = title,
        mediaType = mediaType
    )
}

fun PictureOfDayTable.asPODModel(): PictureOfDay {
    return PictureOfDay(
        url = url,
        title = title,
        mediaType = mediaType
    )
}