package com.example.myresto.restosapp.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RestoranApiService {
    //CALLBACK (standard)
    @GET("restaurants.json")
    fun getRestaurants(): Call<List<RemoteResto>>

    //ALTERNATE (kotlin co routine)
    @GET("restaurants.json")
    suspend fun getRestaurantsCoroutine(): List<RemoteResto>

    @GET("restaurants.json?orderBy=\"r_id\"")
    suspend fun getRestoCoroutine(@Query("equalTo") id:Int): Map<String, RemoteResto>
}
