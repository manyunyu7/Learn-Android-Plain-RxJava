package com.feylabs.rxjava_plain

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/3/movie/popular")
    fun getMovies(): Call<JsonObject>

}