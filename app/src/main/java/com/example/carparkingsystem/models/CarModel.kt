package com.example.carparkingsystem.models

data class CarModel(
    var id: String = "",
    val plate_number: String = "",
    val vehicle_type: String = "",
    val driver_name: String = "",
    val phone_number: String = "",
    val imageUrl: String = "",
    val color: String = "",
    val entry_time: String = "",
)
