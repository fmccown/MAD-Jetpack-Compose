package com.zybooks.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.zybooks.todolist.ui.ToDoApp
import com.zybooks.todolist.ui.theme.ToDoListTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      setContent {
         ToDoListTheme {
            ToDoApp()
         }
      }
   }
}
