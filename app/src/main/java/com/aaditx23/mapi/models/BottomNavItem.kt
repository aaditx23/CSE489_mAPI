package com.aaditx23.mapi.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.EditLocationAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.outlined.AddLocationAlt
import androidx.compose.material.icons.outlined.Audiotrack
import androidx.compose.material.icons.outlined.EditLocationAlt
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.LocationSearching
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    badge: Boolean,
    badgeCount: Int = 0,
    val idx: Int
) {
    var badge = mutableStateOf(badge)
    val badgeCount = mutableIntStateOf(badgeCount)
    object Map: BottomNavItem(
        title = "Map",
        selectedIcon = Icons.Filled.Map,
        unselectedIcon = Icons.Outlined.Map,
        badge = false,
        idx = 0
    )
    object Add: BottomNavItem(
        title = "Add Location",
        selectedIcon = Icons.Filled.AddLocationAlt,
        unselectedIcon = Icons.Outlined.AddLocationAlt,
        badge = false,
        idx = 1
    )
    object Get: BottomNavItem(
        title = "Get Locations",
        selectedIcon = Icons.Filled.LocationSearching,
        unselectedIcon = Icons.Outlined.LocationSearching,
        badge = false,
        idx = 2
    )
    object Edit: BottomNavItem(
        title = "Edit Location",
        selectedIcon = Icons.Filled.EditLocationAlt,
        unselectedIcon = Icons.Outlined.EditLocationAlt,
        badge = false,
        idx = 3
    )


    companion object{
        val bottomNavItemList = listOf(
            Map,
            Add,
            Get,
            Edit
        )
    }



}

