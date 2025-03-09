package com.example.todoapp.ViewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.todoapp.Model.Task

class TaskViewModel: ViewModel(){
    private val _tasks = mutableStateListOf<Task>()
    val tasks : List<Task>
        get() = _tasks

    /**
     * Función que agrega una tarea a la lista de tareas.
     * @param title Título de la tarea a agregar.
     */
    fun addTask(title: String){
        val newId = (_tasks.maxOfOrNull { it.id } ?: 0) + 1 // Si no hay tareas, el id es 0, si hay tareas, el id es el máximo + 1
        _tasks.add(Task(newId, title))
    }
    /**
     * Función que cambia el estado de una tarea.
     * Verifica si la tarea se encuentra en la lista de tareas y cambia su estado.
     * @param task Tarea a cambiar de estado.
     *
     */
    fun toggleTask(task: Task){
        val index = _tasks.indexOf(task)
        if(index != -1){
            _tasks[index] = task.copy(isCompleted = !task.isCompleted)
        }
    }

    /**
     * Función que elimina una tarea de la lista de tareas.
     * @param task Tarea a eliminar.
     */
    fun removeTask(task: Task){
        _tasks.remove(task)
    }
}