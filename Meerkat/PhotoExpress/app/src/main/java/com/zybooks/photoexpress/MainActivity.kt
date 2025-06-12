package com.zybooks.photoexpress

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.zybooks.photoexpress.ui.PhotoExpressApp
import com.zybooks.photoexpress.ui.theme.PhotoExpressTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      setContent {
         PhotoExpressTheme {
            PhotoExpressApp()
         }
      }
   }
}
