package com.mostafa.udacity.asteroidradarapp.utils

object Constants {
    const val API_KEY = "lIFF9chtWddaui6Lu2SLdW9ko7UNtjjsviFe0gpF"

    const val BASE_URL = "https://api.nasa.gov/"
    const val HTTP_GET_NEO_FEED_PATH = "neo/rest/v1/feed"
    const val QUERY_START_DATE_PARAM = "start_date"
    const val QUERY_END_DATE_PARAM = "end_date"
    const val QUERY_API_KEY_PARAM = "api_key"

    const val HTTP_GET_APOD_PATH = "planetary/apod"

    const val API_QUERY_DATE_FORMAT = "yyyy-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
}

object DatabaseConstants {
    const val TABLE_NAME = "asteroid"
    const val POD_NAME = "PictureOfDay"
    const val DATABASE_FILE_NAME = "asteroid.db"
}