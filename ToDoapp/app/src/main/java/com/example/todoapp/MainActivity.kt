package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.DAOs.TaskDao
import com.example.todoapp.Model.Task
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

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}

