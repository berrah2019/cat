package com.example.cat

import retrofit2.Call
import retrofit2.http.GET

interface CatFactService {
    @GET("facts")
    fun getFacts(): Call<Response>
}