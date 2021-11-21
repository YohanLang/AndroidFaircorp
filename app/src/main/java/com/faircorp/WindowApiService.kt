package com.faircorp

import retrofit2.Call
import retrofit2.http.*

interface WindowApiService {
    @GET("windows")
    fun findAll(): Call<List<WindowDto>>

    @GET("windows/{id}")
    fun findById(@Path("id") id: Long): Call<WindowDto>

    @PUT("windows/{id}")
    fun updateWindow(@Path("id") id: Long, @Body window: WindowDto): Call<WindowDto>

    @PUT("windows/{id}/switch")
    fun switch(@Path("id") id: Long): Call<WindowDto>

    @POST("windows/create")
    fun create(@Body window: WindowDto): Call<WindowDto>

    @DELETE("windows/{id}/delete")
    fun delete(@Path("id") id: Long): Call<Void>

}