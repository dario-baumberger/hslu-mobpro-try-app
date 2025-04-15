package com.example.tryapp.business.bands

import android.util.Log
import com.example.tryapp.data.BandsApiService
import com.example.tryapp.domain.band.BandCode
import com.example.tryapp.domain.band.BandInfo
import javax.inject.Inject


class BandsRepositoryImpl @Inject constructor(
    private val api: BandsApiService
) : BandsRepository {
    override suspend fun getBands(): List<BandCode> {
        val response = api.getBandNames()
        if (response.isSuccessful) {
            return response.body().orEmpty()
        } else {
            Log.e("BandsRepository", "API error: ${response.code()} - ${response.message()}")
            throw Exception("Failed to fetch bands: ${response.code()}")
        }
    }

    override suspend fun getBandInfo(code: String): BandInfo? {
        val response = api.getBandInfo(code)
        if (response.isSuccessful) {
            return response.body()
        } else {
            Log.e("BandsRepository", "API error: ${response.code()} - ${response.message()}")
            return null
        }
    }
}
