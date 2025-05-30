package com.zybooks.graduationrsvp

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
import androidx.compose.ui.unit.sp
import com.zybooks.graduationrsvp.ui.theme.GraduationRSVPTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      setContent {
         GraduationRSVPTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
               GradScreen(
                  modifier = Modifier.padding(innerPadding)
               )
            }
         }
      }
   }
}

@Composable
fun GradScreen(modifier: Modifier = Modifier) {
   Text(
      text = "Graduation Announcement",
      //fontSize = 50.sp,
      modifier = modifier
   )
}

@Preview(showBackground = true)
@Composable
fun GradPreview() {
   GraduationRSVPTheme {
      GradScreen()
   }
}

