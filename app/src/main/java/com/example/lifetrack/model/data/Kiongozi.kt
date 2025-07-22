package com.example.lifetrack.model.data

//@JvmOverloads
data class Kiongozi(
    val uuid: String,
    val fullName: String,
    val emailAddress: String,
    val lifetrackID : String = "",
    val passwordHash: String = "",
//    val uuid: String = "",
    val phoneNumber: String = ""
){
    constructor() : this("","",""
    )
}