package com.feylabs.rxjava_plain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.feylabs.rxjava_plain.databinding.ActivityMainBinding
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getMoviesWithoutRxJava()
    }

    fun getMoviesWithoutRxJava() {

        val text = findViewById<TextView>(R.id.texta)

        val req = ApiClient.provideGeneralService().getMovies()
        req.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val body = response.body()
                text.text = "success " + response.body().toString()
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                text.text = "ERROR $t"
            }

        })
    }
}