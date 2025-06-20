package com.zybooks.countdowntimer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import com.zybooks.countdowntimer.ui.TimerScreen
import com.zybooks.countdowntimer.ui.TimerViewModel
import com.zybooks.countdowntimer.ui.theme.CountdownTimerTheme

class MainActivity : ComponentActivity() {
   private lateinit var timerViewModel: TimerViewModel

   private val permissionRequestLauncher =
      registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
         val message = if (isGranted) "Permission granted" else "Permission NOT granted"
         Log.i("MainActivity", message)
      }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      setContent {
         timerViewModel = viewModel()
         CountdownTimerTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
               TimerScreen(
                  timerViewModel = timerViewModel,
                  modifier = Modifier.padding(innerPadding)
               )
            }
         }
      }

      // Only need permission to post notifications on Tiramisu and above
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
         if (ActivityCompat.checkSelfPermission(this,
               Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {
            permissionRequestLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
         }
      }
   }

   override fun onStop() {
      super.onStop()

      // Start TimerWorker if the timer is running
      if (timerViewModel.uiState.value.isRunning) {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this,
                  Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
               startWorker(timerViewModel.uiState.value.remainingMillis)
               timerViewModel.cancelTimer()
            }
         } else {
            startWorker(timerViewModel.uiState.value.remainingMillis)
            timerViewModel.cancelTimer()
         }
      }
   }

   private fun startWorker(millisRemain: Long) {
      val timerWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<TimerWorker>()
         .setInputData(
            workDataOf(
               KEY_MILLIS_REMAINING to millisRemain
            )
         ).build()

      WorkManager.getInstance(applicationContext).enqueue(timerWorkRequest)
   }
}
