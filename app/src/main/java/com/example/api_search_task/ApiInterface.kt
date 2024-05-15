package com.example.api_search_task

import com.example.api_search_task.API.UserResponse
import com.example.api_search_task.API.UserResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("users")
    fun getUsers(): Call<UserResponse>
    @GET("users")
    fun getSpecificId(@Query("username") username: String): Call<UserResponse>
}