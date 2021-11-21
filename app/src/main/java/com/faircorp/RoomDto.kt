package com.faircorp

data class RoomDto(
    val id: Long?,
    val name: String,
    val currentTemperature: Double?,
    val targetTemperature: Double?,
    val floor: Double?,
    val buildingId: Long,

)