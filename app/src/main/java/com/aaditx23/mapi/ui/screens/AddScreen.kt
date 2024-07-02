package com.aaditx23.mapi.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.aaditx23.mapi.backend.Location
import com.aaditx23.mapi.backend.LocationVM
import java.io.File

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Add(file: File){
    val location: LocationVM = viewModel()
    val response by location.locationResponse.collectAsState()
    val context = LocalContext.current
    file.createNewFile()
    file.outputStream().use {
        context.assets.open("im.jpg").copyTo(it)
    }
    var showToast by remember { mutableStateOf(false) }
    val (title, setTitle) = remember { mutableStateOf("") }
    val (lon, setLon) = remember { mutableStateOf("") }
    val (lat, setLat) = remember { mutableStateOf("") }
    val (text4, setText4) = remember { mutableStateOf("") }

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
                location.createLocation(
                    Location(
                        title = title,
                        lat = lat.toDouble(),
                        lon = lon.toDouble()
                    ),
                    image = file
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
}

@Composable
fun Edit(file : File){
    val location: LocationVM = viewModel()
    val response by location.locationResponse.collectAsState()
    val context = LocalContext.current
    file.createNewFile()
    file.outputStream().use {
        context.assets.open("im.jpg").copyTo(it)
    }
    var showToast by remember { mutableStateOf(false) }
    val (id, setId) = remember { mutableStateOf("") }
    val (title, setTitle) = remember { mutableStateOf("") }
    val (lon, setLon) = remember { mutableStateOf("") }
    val (lat, setLat) = remember { mutableStateOf("") }

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
                location.updateLocation(
                    Location(
                        id = id.toInt(),
                        title = title,
                        lat = lat.toDouble(),
                        lon = lon.toDouble()
                    ),
                    image = file
                )
                showToast= false
                Toast.makeText(context, "Updating, please wait", Toast.LENGTH_SHORT).show()


            }) {
            Text(text = "Create")
        }
        LaunchedEffect(response) {
            if (response?.status != null && !showToast){
                Toast.makeText(context, "Edited ${response!!.status}", Toast.LENGTH_SHORT).show()
                showToast = true
            }
        }
    }

}