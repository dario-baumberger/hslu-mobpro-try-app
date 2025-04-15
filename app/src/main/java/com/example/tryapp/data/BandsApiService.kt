package com.example.tryapp.data

import com.example.tryapp.domain.band.BandCode
import com.example.tryapp.domain.band.BandInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BandsApiService {

    @GET("all.json")
    suspend fun getBandNames(): Response<List<BandCode>>

    @GET("info/{code}.json")
    suspend fun getBandInfo(@Path("code") code: String): Response<BandInfo>

}
