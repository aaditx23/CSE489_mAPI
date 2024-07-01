package com.aaditx23.mapi

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.aaditx23.mapi.ui.screens.Main
import com.aaditx23.mapi.ui.theme.MAPITheme
import java.io.File

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MAPITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {  innerPadding ->
                    Main(
                        file = File(cacheDir, "im.jpg")
                    )
                }
            }
        }
    }
}

