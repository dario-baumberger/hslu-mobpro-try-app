package com.example.tryapp.business.bands

import com.example.tryapp.domain.band.BandCode
import com.example.tryapp.domain.band.BandInfo

interface BandsRepository {
    suspend fun getBands(): List<BandCode>
    suspend fun getBandInfo(code: String): BandInfo?
}
