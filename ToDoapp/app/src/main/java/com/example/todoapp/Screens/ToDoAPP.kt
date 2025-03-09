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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.Model.Task
import com.example.todoapp.ViewModels.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoApp(
    viewModel: TaskViewModel = viewModel()
){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

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
                scope.launch {
                    sheetState.show()
                }
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
        if (sheetState.isVisible) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    scope.launch {
                        sheetState.hide()
                    }
                },
            ){
                var text by rememberSaveable { mutableStateOf("") }
                Column(
                    modifier = Modifier.padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
                ){
                    Text("Descripción de la tarea")
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        TextField(
                            value = text,
                            onValueChange = { text = it },
                            modifier = Modifier.fillMaxWidth()
                                .weight(4f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                scope.launch {
                                    if(!text.isNullOrEmpty()){
                                        viewModel.addTask(text)
                                        sheetState.hide()
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            shape = CircleShape
                        ) {
                            Icon(Icons.Default.Check, contentDescription = "Agregar tarea")
                        }
                    }
                }
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