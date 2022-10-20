package com.mostafa.udacity.asteroidradarapp.network

import com.mostafa.udacity.asteroidradarapp.data.model.PictureOfDay
import com.mostafa.udacity.asteroidradarapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET(Constants.HTTP_GET_NEO_FEED_PATH)
    suspend fun getAsteroids(
        @Query(Constants.QUERY_START_DATE_PARAM) startDate: String,
        @Query(Constants.QUERY_END_DATE_PARAM) endDate: String,
        @Query(Constants.QUERY_API_KEY_PARAM) apiKey: String): String

    @GET(Constants.HTTP_GET_APOD_PATH)
    suspend fun getPictureOfDay(@Query(Constants.QUERY_API_KEY_PARAM) apiKey: String) : PictureOfDay
}