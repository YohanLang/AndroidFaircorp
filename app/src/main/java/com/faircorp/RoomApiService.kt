package com.faircorp

import retrofit2.Call
import retrofit2.http.*

interface RoomApiService {
    @GET("rooms")
    fun findAll(): Call<List<RoomDto>>

    @GET("rooms/{id}")
    fun findById(@Path("id") id: Long): Call<RoomDto>

    @PUT("rooms/{id}/switch_Windows")
    fun switch_Windows(@Path("id") id: Long): Call<List<WindowDto>>

    @PUT("rooms/{id}/switch_Heaters")
    fun switch_Heaters(@Path("id") id: Long): Call<List<HeaterDto>>

    @GET("rooms/{id}/windows")
    fun findWindowsByRoom(@Path("id") id: Long): Call<List<WindowDto>>

    @GET("rooms/{id}/heaters")
    fun findHeatersByRoom(@Path("id") id: Long): Call<List<HeaterDto>>

    @PUT("rooms/{id}")
    fun updateRoom(@Path("id") id: Long, @Body Room: RoomDto): Call<RoomDto>

    @POST("rooms/create")
    fun create(@Body room: RoomDto): Call<RoomDto>

    @DELETE("rooms/{id}/delete")
    fun delete(@Path("id") id: Long): Call<Void>
}