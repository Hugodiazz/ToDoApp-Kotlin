package com.example.todoapp.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tareas")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val title: String,
    val isCompleted: Boolean = false
)
