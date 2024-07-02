package com.aaditx23.mapi.backend


import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface LocationService {

    @GET("api.php")
    fun getLocation(): Call<List<Location>>

    @Multipart
    @POST("api.php")
    fun createLocation(
        @Part("title") title: RequestBody,
        @Part("lat") lat: RequestBody,
        @Part("lon") lon: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<Location>

    @DELETE("api.php/{id}")
    fun deleteLocation(@Path("id") id: Int): Call<Void>

    @FormUrlEncoded
    @Multipart
    @PUT("api.php")
    fun updateLocation(
        @Part("id") id: Int,
        @Part("title") title: RequestBody,
        @Part("lat") lat: RequestBody,
        @Part("lon") lon: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<Location>

    @GET("{path}")
    fun getImage(@Path("path") name:String ): Call<Location>

}