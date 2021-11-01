package com.feylabs.rxjava_plain

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    fun provideLogger(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            // development build
            logger.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            // production build
            logger.setLevel(HttpLoggingInterceptor.Level.BASIC)
        }
        return logger
    }


    fun provideClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor(provideLogger())
            .addInterceptor(Interceptor { chain ->

                val original: Request = chain.request()
                val originalHttpUrl: HttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", "2671c64cf6ce8c1265cf8ce1c3543bf9")
                    .build()
                // Request customization: add request headers
                val requestBuilder: Request.Builder = original.newBuilder()
                    .url(url)
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            }).build()

        return client
    }

    fun provideRetrofitClient(): Retrofit {
        val client = Retrofit.Builder()
            .client(provideClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BaseURL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return client
    }

    fun provideGeneralService(): ApiService {
        return provideRetrofitClient().create(ApiService::class.java)
    }
}