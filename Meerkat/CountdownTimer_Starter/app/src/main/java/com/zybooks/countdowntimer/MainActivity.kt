package com.zybooks.countdowntimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.zybooks.countdowntimer.ui.TimerScreen
import com.zybooks.countdowntimer.ui.TimerViewModel
import com.zybooks.countdowntimer.ui.theme.CountdownTimerTheme

class MainActivity : ComponentActivity() {
   private val timerViewModel = TimerViewModel()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      setContent {
         CountdownTimerTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
               TimerScreen(
                  timerViewModel = timerViewModel,
                  modifier = Modifier.padding(innerPadding)
               )
            }
         }
      }
   }
}
