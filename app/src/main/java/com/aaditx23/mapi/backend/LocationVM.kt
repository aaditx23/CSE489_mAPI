package com.aaditx23.mapi.backend

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class LocationVM {

    @SuppressLint("MutableCollectionMutableState")
    private val _allLocations = mutableStateOf(listOf<Location>())
    private val api = Api.retrofitService
    var locationResponse = mutableStateOf(Location())
    val allLocations: State<List<Location>>
        @Composable get() = remember {
            _allLocations
        }

    init{
        getLocation()
    }

    private fun getLocation(){
        val service = api.getLocation()
        service.enqueue(object : Callback<List<Location>>{
            override fun onResponse(call: Call<List<Location>>, response: Response<List<Location>>) {
                if (response.isSuccessful){
                    _allLocations.value = response.body()!!
                }
                else{
                    println("Not successful ${response.errorBody()}")
                }
            }

            override fun onFailure(p0: Call<List<Location>>, error: Throwable) {
                println("Error here in get all${error.printStackTrace()}")
            }

        })
    }

    @Composable
    private fun findLocation(id: Int): Location{
        if (id < allLocations.value.size) {
            return allLocations.value[id - 1]
        }
        return Location()
    }

    fun createLocation(location: Location, image: File){

        val titlePart = location.title?.toRequestBody("text/plain".toMediaTypeOrNull())
        val latPart = location.lat?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())
        val lonPart = location.lon?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())
        val response = api.createLocation(
            title = titlePart!!,
            lat = latPart!!,
            lon = lonPart!!,
            image = MultipartBody.Part
                .createFormData(
                    "image",
                    image.name,
                    image.asRequestBody()
                )
        )
        response.enqueue(object : Callback<Location>{
            override fun onResponse(call: Call<Location>, response: Response<Location>) {
                if (response.isSuccessful){
                    println("POST SUCCESSFUL! ${response.body()!!}")
                    locationResponse = mutableStateOf(response.body()!!)
                }
                else{
                    println("Post Not successful ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Location>, error: Throwable) {
                println("Error here in post ${error.printStackTrace()}")
            }

        })

    }

    fun deleteLocation(id: Int){
        val response = api.deleteLocation(id)
        response.enqueue(object : Callback<Void>{
            override fun onResponse(p0: Call<Void>, p1: Response<Void>) {

                if( response.isExecuted){
                    println("Deleted $id")
                }
                else{
                    println("Coudl not delete $id")
                }
            }

            override fun onFailure(p0: Call<Void>, p1: Throwable) {
                println("Error here in post ${p1.printStackTrace()}")
            }

        })
    }

    fun updateLocation(location: Location, image: File){

        val titlePart = location.title?.toRequestBody("text/plain".toMediaTypeOrNull())
        val latPart = location.lat?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())
        val lonPart = location.lon?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())
        val response = api.updateLocation(
            id = location.id!!,
            title = titlePart!!,
            lat = latPart!!,
            lon = lonPart!!,
            image = MultipartBody.Part
                .createFormData(
                    "image",
                    image.name,
                    image.asRequestBody()
                )
        )
        response.enqueue(object : Callback<Location>{
            override fun onResponse(call: Call<Location>, response: Response<Location>) {
                if (response.isSuccessful){
                    println("PATCH SUCCESSFUL! ${response.body()}")
                }
                else{
                    println("patch Not successful ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Location>, error: Throwable) {
                println("Error here in patch ${error.printStackTrace()}")
            }

        })



    }

}