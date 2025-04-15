package com.example.tryapp.domain.band

import kotlinx.serialization.Serializable

@Serializable
data class BandCode(
    val name: String,
    val code: String
)
