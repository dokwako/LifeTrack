package com.example.lifetrack.model.data

data class Hospital(
    val hospitalId: String,
    val hospitalName: String,
    val hospitalLocation: String
) {
    constructor() : this(
        hospitalId = "",
        hospitalName = "",
        hospitalLocation = ""
    )

}