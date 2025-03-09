package com.example.todoapp.ViewModels

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.todoapp.AppDatabase
import com.example.todoapp.Model.Task
import com.example.todoapp.Repositories.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application){
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "tasks-db"
    ).build()

    private val repository = TaskRepository(db.taskDao())

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks : StateFlow<List<Task>> = _tasks

    init {
        viewModelScope.launch {
            repository.tasksFlow.collect{ taskList ->
                _tasks.value = taskList
            }
        }
    }

    /**
     * Función que agrega una tarea a la lista de tareas.
     * @param title Título de la tarea a agregar.
     */
    fun addTask(title: String){
        viewModelScope.launch {
            repository.insertTask(Task(title = title))
        }
    }
    /**
     * Función que cambia el estado de una tarea.
     * Verifica si la tarea se encuentra en la lista de tareas y cambia su estado.
     * @param task Tarea a cambiar de estado.
     *
     */
    fun toggleTask(task: Task){
        viewModelScope.launch {
            repository.updateTask(task.copy(isCompleted = !task.isCompleted))
        }
    }

    /**
     * Función que elimina una tarea de la lista de tareas.
     * @param task Tarea a eliminar.
     */
    fun removeTask(task: Task){
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}