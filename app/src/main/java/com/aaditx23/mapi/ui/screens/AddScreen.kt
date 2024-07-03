package com.aaditx23.mapi.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.aaditx23.mapi.backend.Location
import com.aaditx23.mapi.backend.LocationVM
import com.aaditx23.mapi.components.ImagePicker
import com.google.android.gms.maps.model.LatLng
import java.io.File

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Add(coordinates: LatLng, file: File){
    val location: LocationVM = viewModel()
    val response by location.locationResponse.collectAsState()
    var selectedImage by remember {
        mutableStateOf<File?>(null)
    }
    val context = LocalContext.current
    file.createNewFile()
    file.outputStream().use {
        context.assets.open("im.jpg").copyTo(it)
    }
    var showToast by remember { mutableStateOf(false) }
    var showImagePicker by remember { mutableStateOf(false) }
    var enableImageSelect by remember { mutableStateOf(true) }

    val (title, setTitle) = remember { mutableStateOf("") }
    val (lon, setLon) = remember { mutableStateOf("${coordinates.longitude}") }
    val (lat, setLat) = remember { mutableStateOf("${coordinates.latitude}") }

    Column(
        modifier = Modifier.padding(top = 20.dp)
    ) {
        TextField(
            value = title,
            onValueChange = {setTitle(it)},
            label = { Text(text = "Title")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        TextField(
            value = lat,
            onValueChange = {setLat(it)},
            label = { Text(text = "Latitude")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        TextField(
            value = lon,
            onValueChange = {setLon(it)},
            label = { Text(text = "Longitude")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        Button(
            onClick = {
                showImagePicker = true
                enableImageSelect = false
            },
            enabled = enableImageSelect
        ) {
            Text(
                text = if (selectedImage == null) "Select Image" else selectedImage!!.name
            )
        }
        Button(
            onClick = {
                location.createLocation(
                    Location(
                        title = title,
                        lat = lat.toDouble(),
                        lon = lon.toDouble()
                    ),
                    image = selectedImage!!
                )
                showToast= false
                Toast.makeText(context, "Adding, please wait", Toast.LENGTH_SHORT).show()


            }) {
                Text(text = "Create")
        }
        LaunchedEffect(response) {
             if (response?.id != null && !showToast){
                 Toast.makeText(context, "Added ${response!!.id}", Toast.LENGTH_SHORT).show()
                 showToast = true
            }
        }
    }
    if (showImagePicker){
        ImagePicker {image ->
            println("CALLED IMAGE PICKER $showImagePicker")
            selectedImage = image
            println("$image FOUND")
        }
    }
}

@Composable
fun Edit(file : File){
    val locationvm: LocationVM = viewModel()
    val response by locationvm.locationResponse.collectAsState()
    val context = LocalContext.current
    file.createNewFile()
    file.outputStream().use {
        context.assets.open("im.jpg").copyTo(it)
    }
    var showToast by remember { mutableStateOf(false) }
    var enableField by remember { mutableStateOf(false) }
    var editObject by remember { mutableStateOf<Location?>(null) }

    val (id, setId) = remember { mutableStateOf("0") }
    val (title, setTitle) = remember { mutableStateOf("") }
    val (lon, setLon) = remember { mutableStateOf("0") }
    val (lat, setLat) = remember { mutableStateOf("0") }


    Column(
        modifier = Modifier.padding(top = 20.dp)
    ) {
        TextField(
            value = id,
            onValueChange = {setId(it)},
            label = { Text(text = "ID")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        Button(
            onClick = {
                editObject = locationvm.findLocation(id.toInt())
                if(editObject != null){
                    setTitle(editObject!!.title!!)
                    setLat(editObject!!.lat!!.toString())
                    setLon(editObject!!.lon!!.toString())
                    enableField = true
                }
                else{
                    Toast.makeText(context, "Data with ID $id Does not exist", Toast.LENGTH_SHORT).show()
                }

                }
        ) {
            Text(text = "Find Data")
        }
        if(enableField){
            AsyncImage(
                model ="https://labs.anontech.info/cse489/t3/${editObject!!.image}",
                contentDescription = "image",
                modifier = Modifier
                    .size(200.dp)
            )
        }
        TextField(
            value = title,
            onValueChange = {setTitle(it)},
            label = { Text(text = "Title")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            enabled = enableField
        )
        TextField(
            value = lat,
            onValueChange = {setLat(it)},
            label = { Text(text = "Latitude")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            enabled = enableField
        )
        TextField(
            value = lon,
            onValueChange = {setLon(it)},
            label = { Text(text = "Longitude")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            enabled = enableField
        )
        Button(
            onClick = {
                println("$id $title $lat $lon")
                locationvm.updateLocation(
                    Location(

                        id = id.toInt(),
                        title = title,
                        lat = lat.toDouble(),
                        lon = lon.toDouble()
                    )
                )
                showToast= false
                Toast.makeText(context, "Updating, please wait", Toast.LENGTH_SHORT).show()


            }) {
            Text(text = "Update")
        }
        LaunchedEffect(response) {
            if (response?.status != null && !showToast){
                Toast.makeText(context, "Edited ${response!!.status}", Toast.LENGTH_SHORT).show()
                showToast = true
            }
        }
    }

}