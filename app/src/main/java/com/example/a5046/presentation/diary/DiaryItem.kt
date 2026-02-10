package com.example.a5046.presentation.diary

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.a5046.R
import com.example.a5046.components.InactiveButton
import com.example.a5046.database.model.Diary
import com.example.a5046.presentation.Routes
import com.example.a5046.presentation.moodtracker.softGreen
import com.example.a5046.ui.theme.ForestGreen
import com.example.a5046.ui.theme.SoftGreen

@Composable
fun DiaryItemScreen(navController: NavHostController,
                    viewModels: DiaryViewModel,
                    entryId: Int,
) {
    var viewModel = viewModel<DiaryViewModel>()
    LaunchedEffect(true) {
        if (entryId != -1) {
            viewModel.getEntry(entryId)
        }
    }
    Log.i("entryID", entryId.toString())
    val state = viewModel.uiState
    var title by remember { mutableStateOf(state?.entry?.title ?: "") }
    Log.i("title, in viewModel", state.entry?.title.toString())
    var content by rememberSaveable { mutableStateOf(state.entry?.content ?: "") }
    var date by rememberSaveable {
        mutableLongStateOf(
            state.entry?.createdDate ?: System.currentTimeMillis()
        )
    }
    var openDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(state.entry) {
        if (state.entry != null && title.isBlank() && content.isBlank()) {
            title = state.entry.title
            content = state.entry.content
            date = state.entry.createdDate
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if(title.isBlank()) "New Diary" else title)},
                actions = {
                    IconButton(onClick = {navController.navigate(Routes.Diary.value) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_left),
                            contentDescription = "go back"
                        )
                    }
                    if(entryId!= -1)
                        IconButton(onClick = { openDialog = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = "delete"
                            )
                        }
                },
                backgroundColor = SoftGreen
                )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    var entry = Diary(
                        title = title,
                        content = content,
                        createdDate = date,
                        updatedDate = System.currentTimeMillis()
                    )
                    if(entryId == -1)
                        viewModel.addDiary(entry)
                    else
                        entry = Diary(
                            title = title,
                            content = content,
                            createdDate = date,
                            updatedDate = System.currentTimeMillis(),
                            id = entryId
                        )
                        viewModel.updateDiary(entry)
                    navController.navigate(Routes.Diary.value)
                },
                backgroundColor = softGreen,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_save),
                    contentDescription = "Save",
                    modifier = Modifier.size(25.dp),
                    tint = Color.White
                )
            }
        }) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(12.dp)
                .padding(paddingValues)
        ) {
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Hey there!\n How's your day been? ") },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(vertical = 5.dp).padding(12.dp),
            )
            if (openDialog)
                AlertDialog(
                    shape = RoundedCornerShape(25.dp),
                    onDismissRequest = { openDialog = false },
                    title = { Text("Delete Diary") },
                    text = {
                        Text("Are you sure to delete this diary?")

                    },
                    confirmButton = {
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = ForestGreen),
                            shape = RoundedCornerShape(25.dp),
                            onClick = {
                                viewModel.deleteDiary(state.entry!!)
                                openDialog = false
                                navController.popBackStack(route = Routes.Diary.value, inclusive = false)
                            },
                        ) {
                            Text(
                                "Delete",
                                color = Color.White
                            )
                        }
                    },
                    dismissButton = {
                        Button(
                            colors = buttonColors(SoftGreen),
                            shape = RoundedCornerShape(25.dp),
                            onClick = {
                                openDialog = false
                            }) {
                            Text(
                                "Cancel",
                                color = Color.White
                            )
                        }
                    }
                )
        }
    }
}


