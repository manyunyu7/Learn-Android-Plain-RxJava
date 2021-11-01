package com.feylabs.rxjava_plain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.feylabs.rxjava_plain.databinding.ActivityMainBinding
import com.google.gson.JsonObject
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import io.reactivex.schedulers.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getMoviesRxJavaWithObservable()
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

    private fun getMoviesRxJavaWithObservable() {

        val text = findViewById<TextView>(R.id.texta)
        val req = ApiClient.provideGeneralService().getMoviesWithRxJavaWithObservable()

        req.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<JsonObject> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: JsonObject) {
                    var all = ""
                    val results = t.getAsJsonArray("results")
                    results.forEachIndexed { index, jsonElement ->
                        val obj = jsonElement.asJsonObject
                        val name: String = obj.get("title").asString
                        all += "${index + 1} $name \n"
                    }
                    text.text = all
                }

                override fun onError(e: Throwable) {
                    text.text = e.toString()
                }

                override fun onComplete() {

                }

            })
    }
}