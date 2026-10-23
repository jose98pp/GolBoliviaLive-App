package com.example.golbolivialive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.golbolivialive.ui.theme.GolBoliviaLiveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GolBoliviaLiveTheme {
                GolBoliviaApp()
            }
        }
    }
}
