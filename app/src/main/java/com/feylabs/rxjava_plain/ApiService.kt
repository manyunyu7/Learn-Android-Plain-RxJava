package com.feylabs.rxjava_plain

import okhttp3.ResponseBody
import retrofit2.http.GET

interface ApiService {

    @GET("/movie/popular")
    fun getMovies():
            ResponseBody

}