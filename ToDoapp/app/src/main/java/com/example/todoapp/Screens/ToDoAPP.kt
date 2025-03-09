package com.example.todoapp.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.Model.Task
import com.example.todoapp.ViewModels.TaskViewModel

@Composable
fun ToDoApp(
    viewModel: TaskViewModel = viewModel()
){
    var tasks = viewModel.tasks
    val pendingTasks = tasks.filter { !it.isCompleted }
    val completedTasks = tasks.filter { it.isCompleted }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
        .verticalScroll(
            enabled = true,
            state = rememberScrollState()
        )
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text("Tareas",
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f))
            Button(onClick = {
                viewModel.addTask("Nueva tarea")
            },
                modifier = Modifier.weight(1f)
            ){
                Text("Agregar tarea")
                Icon(Icons.Filled.Add, contentDescription = "Agregar tarea")
            }
        }
        if(tasks.isEmpty()) {
            EmptyTasks(
                modifier = Modifier.weight(1f)
            )
        }else{
            if (pendingTasks.isEmpty()) {
                Text(text = "No existen tareas pendientes")
            } else {
                TasksList(
                    header = "Tareas Pendientes",
                    tasks = pendingTasks,
                    onTaskCheckedChange = { viewModel.toggleTask(it) },
                    modifier = Modifier.fillMaxWidth(),
                    onTaskDeleted = { viewModel.removeTask(it) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (completedTasks.isEmpty()) {
                Text(text = "Aún no has completado ninguna tarea")
            } else {
                TasksList(
                    header = "Tareas Completadas",
                    tasks = completedTasks,
                    onTaskCheckedChange = { viewModel.toggleTask(it) },
                    modifier = Modifier.fillMaxWidth(),
                    onTaskDeleted = { viewModel.removeTask(it) }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun EmptyTasks(
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("No existen tareas\nAgrega una nueva tarea para comenzar", textAlign = TextAlign.Center, fontSize = MaterialTheme.typography.titleLarge.fontSize)
    }
}

@Composable
fun TasksList(
    header: String,
    tasks: List<Task>,
    onTaskCheckedChange: (Task) -> Unit,
    onTaskDeleted: (Task) -> Unit,
    modifier: Modifier
){
    Column(modifier){
        Text(text = header, fontSize = MaterialTheme.typography.titleLarge.fontSize)
        Spacer(modifier = Modifier.height(4.dp))
        tasks.forEach { task ->
            TaskItem(task = task, onTaskCheckedChange, onTaskDeleted)
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onTaskCheckedChange: (Task) -> Unit,
    onTaskDeleted: (Task) -> Unit
){
    Card(
        onClick = {
            onTaskCheckedChange(task)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth().
                padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = {
                    onTaskCheckedChange(task)
                }
            )
            Spacer( modifier = Modifier.width(8.dp) )
            Text(text = task.title, modifier = Modifier.weight(1f))
            IconButton(onClick = {
                onTaskDeleted(task)
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar tarea")
            }
        }
    }
}