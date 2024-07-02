package com.aaditx23.mapi.backend

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class LocationVM: ViewModel() {

    @SuppressLint("MutableCollectionMutableState")
    private val _allLocations = mutableStateOf(listOf<Location>())
    private val _locationResponse = MutableStateFlow<Location?>(null)
    val locationResponse: StateFlow<Location?>
    @Composable    get() = remember{_locationResponse}
    private val api = Api.retrofitService


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


    private fun findLocation(id: Int): Location{
        if (id < _allLocations.value.size) {
            return _allLocations.value[id - 1]
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
                    _locationResponse.value = response.body()
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
        val oldLocation = findLocation(id = location.id!!)

        val titlePart = (location.title ?: oldLocation.title)!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val latPart = (location.lat?: oldLocation.lat).toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val lonPart = (location.lon?: oldLocation.lat).toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val response = api.updateLocation(
            id = location.id,
            title = titlePart,
            lat = latPart,
            lon = lonPart,
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
                    _locationResponse.value = response.body()
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

    fun getImage(name: String){
        val response = api.getImage(name)
        response.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(p0: Call<ResponseBody>, p1: Response<ResponseBody>) {
                TODO("Not yet implemented")
            }

            override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}