package com.example.lifetrack.model.data

data class Patient(
    val id: String,
    val name: String,
    val age: Int,
    val gender: String,
    val bloodPressure: String,
    val lastVisit: String,
    val condition: String
)