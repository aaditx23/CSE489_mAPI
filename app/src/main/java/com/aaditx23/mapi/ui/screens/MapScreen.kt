package com.aaditx23.mapi.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.aaditx23.mapi.backend.Location
import com.aaditx23.mapi.backend.LocationService
import com.aaditx23.mapi.backend.LocationVM
import com.aaditx23.mapi.components.permissionLauncher
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState


@SuppressLint("UnrememberedMutableState", "MissingPermission")
@Composable
fun Map(setLocation: (coordinates: LatLng) -> Unit){


    val locationvm : LocationVM = viewModel()
    val context = LocalContext.current

    var hasLocationPermission by remember { mutableStateOf(false) }
    hasLocationPermission = permissionLauncher(context = context, permission = Manifest.permission.ACCESS_FINE_LOCATION )



    val cameraPositonState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(LatLng(23.6850, 90.3563), 12f)
    }
    var showDialog by remember{
        mutableStateOf(false)
    }
    var markerLocation by remember{
        mutableStateOf<Location?>(null)
    }
    var userLocation by remember{
        mutableStateOf<LatLng?>(null)
    }

    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission){
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation.addOnSuccessListener {location ->
                if (location != null){
                    userLocation = LatLng(location.latitude, location.longitude)
                    setLocation(userLocation!!)
                    cameraPositonState.position = CameraPosition.fromLatLngZoom(userLocation!!, 15f)
                }
            }
        }
    }


    Box{

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositonState,
        ){
            locationvm.allLocations.value.forEachIndexed { _, location ->
                val markerState = rememberMarkerState(
                     position = LatLng(location.lat!!, location.lon!!)
                )
                Marker(
                    state = markerState,
                    title = location.title,
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET),
                    onClick = {
                        showDialog = true
                        markerLocation = location
                        true
                    }
                )
            }
            userLocation?.let{
                val userMarkerState = rememberMarkerState(position = it)
                Marker(
                    state = userMarkerState,
                    title = "CURRENT LOCATION"
                )
            }
            
            
        }

    }
    if(showDialog){
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(text = markerLocation?.title ?: "", fontWeight = FontWeight.Bold)
            },
            text = {
                Column {
                    AsyncImage(
                        model = "https://labs.anontech.info/cse489/t3/${markerLocation!!.image}",
                        contentDescription = markerLocation?.title,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(BorderStroke(2.dp, Color.Gray), RoundedCornerShape(8.dp))
                            .clickable { showDialog = true }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Close")
                }
            }
        )
    }


}