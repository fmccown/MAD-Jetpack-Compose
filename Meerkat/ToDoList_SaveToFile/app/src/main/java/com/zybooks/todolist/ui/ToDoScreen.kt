package com.zybooks.todolist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.todolist.R
import com.zybooks.todolist.Task
import com.zybooks.todolist.ui.theme.ToDoListTheme

@Composable
fun ToDoScreen(
   modifier: Modifier = Modifier,
   todoViewModel: ToDoViewModel = viewModel(
      factory = ToDoViewModel.Factory
   ),
   onClickSettings: () -> Unit = {}
) {
   Scaffold(
      topBar = {
         ToDoAppTopBar(
            completedTasksExist = todoViewModel.completedTasksExist,
            onDeleteCompletedTasks = todoViewModel::deleteCompletedTasks,
            onCreateTasks = todoViewModel::createTestTasks,
            archivedTasksExist = todoViewModel.archivedTasksExist,
            onRestoreArchive = todoViewModel::restoreArchivedTasks,
            onClickSettings = onClickSettings,
            confirmDelete = todoViewModel.confirmDelete
         )
      }
   ) { innerPadding ->
      Column(
         modifier = modifier
            .fillMaxSize()
            .padding(innerPadding),
      ) {
         AddTaskInput(todoViewModel::addTask)
         TaskList(
            taskList = todoViewModel.taskList,
            onDeleteTask = todoViewModel::deleteTask,
            onArchiveTask = todoViewModel::archiveTask,
            onToggleTaskComplete = todoViewModel::toggleTaskCompleted
         )
      }
   }

   LaunchedEffect(Unit) {
      todoViewModel.initTaskList()
   }
}

@Composable
fun TaskList(
   taskList: List<Task>,
   onDeleteTask: (Task) -> Unit,
   onArchiveTask: (Task) -> Unit,
   onToggleTaskComplete: (Task) -> Unit
) {
   LazyColumn {
      items(
         items = taskList,
         key = { task -> task.id }
      ) { task ->
         val currentTask by rememberUpdatedState(task)
         val dismissState = rememberSwipeToDismissBoxState(
            confirmValueChange = {
               when (it) {
                  SwipeToDismissBoxValue.StartToEnd -> {
                     onDeleteTask(currentTask)
                     true
                  }

                  SwipeToDismissBoxValue.EndToStart -> {
                     onArchiveTask(currentTask)
                     true
                  }

                  else -> false
               }
            }
         )

         SwipeToDismissBox(
            state = dismissState,
            backgroundContent = { SwipeBackground(dismissState) },
            modifier = Modifier
               .padding(vertical = 1.dp)
               .animateItem(),
            content = {
               TaskCard(
                  task = task,
                  toggleCompleted = onToggleTaskComplete
               )
            }
         )
      }
   }
}

@Composable
fun TaskCard(
   task: Task,
   toggleCompleted: (Task) -> Unit,
   modifier: Modifier = Modifier
) {
   Card(
      modifier = modifier
         .padding(8.dp)
         .fillMaxWidth(),
      colors = CardDefaults.cardColors(
         containerColor = MaterialTheme.colorScheme.surfaceVariant
      )
   ) {
      Row(
         modifier = modifier.fillMaxWidth(),
         verticalAlignment = Alignment.CenterVertically,
         horizontalArrangement = Arrangement.SpaceBetween
      ) {
         Text(
            text = task.body,
            modifier = modifier.padding(start = 12.dp),
            color = if (task.completed) Color.Gray else Color.Black
         )
         Checkbox(
            checked = task.completed,
            onCheckedChange = {
               toggleCompleted(task)
            }
         )
      }
   }
}

@Composable
fun SwipeBackground(
   dismissState: SwipeToDismissBoxState,
   modifier: Modifier = Modifier
) {
   val color = when (dismissState.dismissDirection) {
      SwipeToDismissBoxValue.EndToStart -> Color.Green
      SwipeToDismissBoxValue.StartToEnd -> Color.Red
      SwipeToDismissBoxValue.Settled -> Color.Transparent
   }

   Row(
      modifier
         .fillMaxSize()
         .background(color)
         .padding(horizontal = 15.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
   ) {
      if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd) {
         Icon(
            Icons.Default.Delete,
            contentDescription = "Delete",
         )
      }
      Spacer(modifier = Modifier)
      if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
         Icon(
            painter = painterResource(R.drawable.archive),
            contentDescription = "Archive",
         )
      }
   }
}

@Composable
fun AddTaskInput(onEnterTask: (String) -> Unit) {
   val keyboardController = LocalSoftwareKeyboardController.current
   var taskBody by remember { mutableStateOf("") }

   OutlinedTextField(
      modifier = Modifier
         .fillMaxWidth()
         .padding(6.dp),
      value = taskBody,
      onValueChange = { taskBody = it },
      label = { Text("Enter task") },
      keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
      keyboardActions = KeyboardActions(
         onDone = {
            onEnterTask(taskBody)
            taskBody = ""
            keyboardController?.hide()
         }
      )
   )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoAppTopBar(
   completedTasksExist: Boolean,
   onDeleteCompletedTasks: () -> Unit,
   archivedTasksExist: Boolean,
   onRestoreArchive: () -> Unit,
   onCreateTasks: () -> Unit,
   onClickSettings: () -> Unit,
   confirmDelete: Boolean = true
) {
   var showDeleteTasksDialog by remember { mutableStateOf(false) }

   if (showDeleteTasksDialog) {
      DeleteTasksDialog(
         onDismiss = {
            showDeleteTasksDialog = false
         },
         onConfirm = {
            showDeleteTasksDialog = false
            onDeleteCompletedTasks()
         }
      )
   }

   TopAppBar(
      colors = topAppBarColors(
         containerColor = MaterialTheme.colorScheme.primaryContainer,
         titleContentColor = MaterialTheme.colorScheme.primary,
      ),
      title = {
         Text(text = "To-Do List")
      },
      actions = {
         IconButton(onClick = onCreateTasks) {
            Icon(
               Icons.Default.Add,
               contentDescription = "Add Tasks"
            )
         }
         IconButton(
            onClick = onRestoreArchive,
            enabled = archivedTasksExist
         ) {
            Icon(
               Icons.Default.Refresh,
               contentDescription = "Restore Archived Tasks"
            )
         }
         IconButton(
            onClick = {
               if (confirmDelete) {
                  showDeleteTasksDialog = true
               } else {
                  onDeleteCompletedTasks()
               }
            },
            enabled = completedTasksExist
         ) {
            Icon(
               Icons.Default.Delete,
               contentDescription = "Delete"
            )
         }
         IconButton(onClick = onClickSettings) {
            Icon(
               Icons.Default.Settings,
               contentDescription = "Settings"
            )
         }
      }
   )
}


@Preview(showBackground = true)
@Composable
fun ToDoScreenPreview() {
   val viewModel = viewModel<ToDoViewModel>()
   viewModel.createTestTasks()
   ToDoListTheme {
      ToDoScreen(todoViewModel = viewModel)
   }
}