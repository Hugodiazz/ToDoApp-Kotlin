package com.example.todoapp.Model

data class Task(
    val id: Int,
    val title: String,
    val isCompleted: Boolean = false
)
