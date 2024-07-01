package com.aaditx23.mapi.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.aaditx23.mapi.backend.Location
import com.aaditx23.mapi.backend.LocationVM
import org.json.JSONObject
import java.io.File


@Composable
fun Main(file: File) {
    val context = LocalContext.current
    val location = LocationVM()
    val locationData = location.allLocations.value
    file.createNewFile()
    file.outputStream().use {
        context.assets.open("im.jpg").copyTo(it)
    }
    val locationJSON = JSONObject()
    locationJSON.put("title", "Demo 4 - 23341077")
    locationJSON.put("lat", 23.6850)
    locationJSON.put("lon", 90.3563)
    locationJSON.put("image", file)





    Column(

    ) {
        locationData.forEachIndexed { _, location ->
            Text(text = location.title.toString())
        }
        location.updateLocation(
            location = Location(
                id = 61,
                title = "Demo 6 update - 23341077",
                lon = 90.3563,
                lat = 23.6850
            ),
            image = file
        )
    }


}