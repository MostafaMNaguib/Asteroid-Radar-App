package com.mostafa.udacity.asteroidradarapp.network

import com.mostafa.udacity.asteroidradarapp.data.model.Asteroid
import com.mostafa.udacity.asteroidradarapp.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object NetworkBuilder {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Constants.BASE_URL)
        .build()

    private val retrofitService : ApiServices by lazy {
        retrofit.create(ApiServices::class.java)
    }

suspend fun getAsteroids() : List<Asteroid> {
    val responseStr = retrofitService.getAsteroids("","", Constants.API_KEY)
    val responseJsonObject = JSONObject(responseStr)

    return parseAsteroidsJsonResult(responseJsonObject)
}

    suspend fun getPictureOfDay() = retrofitService.getPictureOfDay(Constants.API_KEY)
}