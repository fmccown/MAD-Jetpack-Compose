package com.zybooks.findme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.zybooks.findme.ui.CheckPermission
import com.zybooks.findme.ui.FindMeApp
import com.zybooks.findme.ui.theme.FindMeTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      setContent {
         FindMeTheme {
            CheckPermission(
               permission = android.Manifest.permission.ACCESS_FINE_LOCATION,
               onGranted = { FindMeApp() }
            )
         }
      }
   }
}
