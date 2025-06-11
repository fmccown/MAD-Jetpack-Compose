package com.zybooks.compass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.zybooks.compass.ui.CompassApp
import com.zybooks.compass.ui.theme.CompassTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      setContent {
         CompassTheme {
            CompassApp()
         }
      }
   }
}
