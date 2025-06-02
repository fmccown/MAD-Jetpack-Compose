package com.zybooks.petadoption

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.zybooks.petadoption.ui.PetApp
import com.zybooks.petadoption.ui.theme.PetAdoptionTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      setContent {
         PetAdoptionTheme {
            PetApp()
         }
      }
   }
}
