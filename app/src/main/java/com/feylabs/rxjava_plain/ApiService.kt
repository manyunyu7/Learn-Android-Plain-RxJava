package com.feylabs.rxjava_plain

import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/3/movie/popular")
    fun getMovies(): Call<JsonObject>

    @GET("/3/movie/popular")
    fun getMoviesWithRxJavaWithObservable(): Observable<JsonObject>

    @GET("/3/movie/popular")
    fun getMoviesWithRxJavaWithSingle(): Single<JsonObject>

}