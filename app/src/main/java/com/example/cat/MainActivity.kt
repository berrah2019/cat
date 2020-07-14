package com.example.cat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val moshi = Moshi.Builder().build()
        val logging = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )
        val client = OkHttpClient.Builder().addInterceptor(logging)
            .build()
        val retrofit = Retrofit.Builder().client(client)
            .baseUrl("https://cat-fact.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        val catServices = retrofit.create(CatFactService::class.java)

        getFactButton.setOnClickListener {
            getFactButton.isEnabled=false
            val call=catServices.getFacts()
          call.enqueue(object:retrofit2.Callback<Response>{
              override fun onFailure(call: Call<Response>, t: Throwable) {
                  getFactButton.isEnabled=true
                  errorView.isVisible=true
              }

              override fun onResponse(
                  call: Call<Response>,
                  response: retrofit2.Response<Response>
              ) {
                  getFactButton.isEnabled=true
                  errorView.isVisible=false
                  val listResponse=response.body()!!.all
                  val random= Random.nextInt(0,listResponse.size-1)
                  catFactView.text=listResponse[random].text

              }

          })







        }
    }
}



