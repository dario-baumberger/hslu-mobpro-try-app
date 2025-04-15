package com.example.tryapp.domain.band

import kotlinx.serialization.Serializable

@Serializable
data class BandInfo(
    val name: String,
    val foundingYear: Int,
    val homeCountry: String,
    val bestOfCdCoverImageUrl: String?
)
