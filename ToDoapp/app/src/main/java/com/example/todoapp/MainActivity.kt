package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.Screens.ToDoApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppTheme {
                // A surface container using the 'background' color from the theme
                Scaffold{padingValues ->
                    ToDoApp()
                }
            }
        }
    }
}

