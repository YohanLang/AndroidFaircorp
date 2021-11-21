package com.faircorp

import retrofit2.Call
import retrofit2.http.*

interface HeaterApiService {
    @GET("heaters")
    fun findAll(): Call<List<HeaterDto>>

    @GET("heaters/{id}")
    fun findById(@Path("id") id: Long): Call<HeaterDto>

    @PUT("heaters/{id}")
    fun updateHeater(@Path("id") id: Long, @Body heater: HeaterDto): Call<HeaterDto>

    @PUT("heaters/{id}/switch")
    fun switch(@Path("id") id: Long ): Call<HeaterDto>

    @POST("heaters/create")
    fun create( @Body heater:HeaterDto): Call<HeaterDto>

    @DELETE("heaters/{id}/delete")
    fun delete(@Path("id") id : Long): Call<Void>
}