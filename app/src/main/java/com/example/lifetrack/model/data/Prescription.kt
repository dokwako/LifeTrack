package com.example.lifetrack.model.data

data class Prescription(
    val medication: String,
    val dosage: String,
    val duration: String,
    val notes: String = ""
)