package com.zybooks.pizzaparty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.zybooks.pizzaparty.ui.PizzaPartyScreen
import com.zybooks.pizzaparty.ui.theme.PizzaPartyTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      setContent {
         PizzaPartyTheme(dynamicColor = false) {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
               PizzaPartyScreen(
                  modifier = Modifier.padding(innerPadding)
               )
            }
         }
      }
   }
}
