package com.faircorp

import retrofit2.Call
import retrofit2.http.*

interface BuildingApiService {
    @GET("buildings")
    fun findAll(): Call<List<BuildingDto>>

    @GET("buildings/{id}")
    fun findById(@Path("id") id: Long): Call<BuildingDto>

    @PUT("buildings/{id}")
    fun updateBuilding(@Path("id") id: Long, @Body building: BuildingDto): Call<BuildingDto>

    @POST("buildings/create")
    fun create(@Body building: BuildingDto): Call<BuildingDto>

    @GET("buildings/{id}/rooms")
    fun findRoomByBuilding(@Path("id") id: Long): Call<List<RoomDto>>

    @DELETE("buildings/{id}/delete")
    fun delete(@Path("id") id: Long): Call<Void>
}