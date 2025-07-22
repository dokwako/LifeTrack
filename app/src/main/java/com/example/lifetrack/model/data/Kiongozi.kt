package com.example.lifetrack.model.data

//@JvmOverloads
data class Kiongozi(
    var uuid: String = "",
    val fullName: String,
    val emailAddress: String,
    val lifetrackID : String = "",
    var passwordHash: String = "",
    var phoneNumber: String = ""
){
    constructor() : this("","",""
    )
}