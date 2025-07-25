package com.example.lifetrack.model.data

data class LabTest(
    val name: String,
    val date: String,
    val results: Map<String, String> // Key: Test parameter, Value: Result value
)