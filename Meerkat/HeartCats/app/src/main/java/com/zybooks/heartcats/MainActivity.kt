package com.zybooks.heartcats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.zybooks.heartcats.ui.CatsApp
import com.zybooks.heartcats.ui.theme.HeartCatsTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      setContent {
         HeartCatsTheme {
            CatsApp()
         }
      }
   }
}
