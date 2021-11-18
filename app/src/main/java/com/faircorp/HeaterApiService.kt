package com.faircorp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface HeaterApiService {
    @GET("heaters")
    fun findAll(): Call<List<HeaterDto>>
    @GET("heaters/{id}")
    fun findById(@Path("id") id: Long): Call<HeaterDto>
    @PUT("heaters/{id}")
    fun updateHeater(@Path("id") id: Long, @Body heater: HeaterDto): Call<HeaterDto>
}