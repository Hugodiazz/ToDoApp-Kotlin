package com.example.todoapp.Repositories

import com.example.todoapp.DAOs.TaskDao
import com.example.todoapp.Model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(
    private val taskDao: TaskDao)
{
    val tasksFlow: Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun insertTask(task: Task){
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }
}