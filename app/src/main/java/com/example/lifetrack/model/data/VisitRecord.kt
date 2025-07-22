package com.example.lifetrack.model.data

data class VisitRecord(
    val date: String,
    val diagnosis: String,
    val medications: String
)

{
    constructor() : this("", "", "")
}

//    fun toMap(): Map<String, String> {
//        return mapOf(
//            "date" to date,
//            "diagnosis" to diagnosis,
//            "medications" to medications
//        )
//    }


