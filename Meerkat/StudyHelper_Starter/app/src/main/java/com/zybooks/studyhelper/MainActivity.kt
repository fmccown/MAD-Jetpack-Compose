package com.zybooks.studyhelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.zybooks.studyhelper.ui.StudyHelperApp
import com.zybooks.studyhelper.ui.theme.StudyHelperTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      setContent {
         StudyHelperTheme {
            StudyHelperApp()
         }
      }
   }
}

