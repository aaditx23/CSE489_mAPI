package com.aaditx23.mapi.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aaditx23.mapi.backend.Location
import com.aaditx23.mapi.backend.LocationVM
import com.aaditx23.mapi.components.BottomNavigation
import com.aaditx23.mapi.models.BottomNavItem
import com.aaditx23.mapi.models.BottomNavItem.Companion.bottomNavItemList
import org.json.JSONObject
import java.io.File


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Main(file: File) {
//fun Main(){
    val location = LocationVM()
    val bottomNav = bottomNavItemList
    var selectedIndex by rememberSaveable {
        mutableIntStateOf(1)
    }
    val navController = rememberNavController()


    Scaffold(
        bottomBar = {
            BottomNavigation(selectedIndex = selectedIndex) {index ->
                selectedIndex = index
                navController.navigate(bottomNav[index].title)
            }
        }
    ) {

        NavHost(navController = navController, startDestination = "Add Location"){
            composable("Map"){
                Map()
            }
            composable("Add Location"){
                Add(file)
            }
            composable("Edit Location"){
                Edit(file)
            }
            composable("Get Locations"){
                Get()
            }
        }
    }


}