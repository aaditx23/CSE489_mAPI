package com.aaditx23.mapi.backend

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Api {

    private const val URL = "https://labs.anontech.info/cse489/t3/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(URL)
        .build()

    val retrofitService: LocationService by lazy {
        retrofit.create(LocationService::class.java)
    }

}