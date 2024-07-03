package com.aaditx23.mapi.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.aaditx23.mapi.backend.Location
import com.aaditx23.mapi.backend.LocationVM

@Composable
fun Get(){
    val locationvm: LocationVM = viewModel()
    val allLocation = locationvm.allLocations.value
    println("SIZE: ${allLocation.size}")


    LazyColumn(
        modifier = Modifier.padding(top = 20.dp, bottom = 150.dp)
    ) {
        items(allLocation){location ->

            Data(location = location)

            println(location.image)
        }
    }


}

@Composable
fun Data(location: Location){

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
    ) {



            Column {
                AsyncImage(
                    model ="https://labs.anontech.info/cse489/t3/${location.image}",
                    contentDescription = "image",
                    modifier = Modifier
                        .size(200.dp)
                )
                Text(text = location.id!!.toString())
                Text(text = location.title!!)
                Text(text = location.lat.toString())
                Text(text = location.lon.toString())
                Text(text = location.image.toString())

            }

    }
}