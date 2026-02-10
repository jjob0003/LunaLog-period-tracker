package com.example.a5046.presentation.diary

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.a5046.R
import com.example.a5046.database.model.Diary

import com.example.a5046.presentation.Routes
import com.example.a5046.presentation.moodtracker.softGreen
import com.example.a5046.presentation.util.Constants
import com.example.a5046.ui.theme.ForestGreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Date

@Composable
fun DiaryScreen(
    navController: NavHostController,
    viewModels: DiaryViewModel
) {
    val uiState = viewModels.uiState

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController.navigate(
                        Routes.DiaryItem.value.replace("{ID}", "${-1}")
                    )},
                backgroundColor = softGreen,
            ) {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
    ) {
        PaddingValues ->
        val weather by viewModels.weather

        runBlocking {
            val job: Job = launch(context = Dispatchers.Default) {
                viewModels.getResponse()
                delay(100)
            }
            job.join()
        }

        Column {
            Spacer(Modifier.height(8.dp))
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (!weather.daily.isJsonNull) {

                    Text(
                        "Today's Weather : " +
                                "${weather.daily.getAsJsonArray("temperature_2m_min")?.get(0) ?: "Loading..."}" +
                                "° - " +
                                " ${weather.daily.getAsJsonArray("temperature_2m_max")?.get(0) ?: "Loading..."}" + "°",
                        style = androidx.compose.material3.MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = ForestGreen,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )

                }
            }

            if (uiState.entries.isEmpty())
                NoEntriesMessage()
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(12.dp)
            ) {
                items(uiState.entries, key = { it.id }) { entry ->
                    DiaryEntryItem(
                        entry = entry,
                        onClick = {
                        navController.navigate(
                            Routes.DiaryItem.value.replace("{ID}", "${it.id}")
                        )
                        }
                    )
                }

            }
        }
    }
}



@Composable
fun NoEntriesMessage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Empty diaries? Time to fill them up! \n Click the + button and let the storytelling begin!",
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Image(
            modifier = Modifier.size(125.dp),
            painter = painterResource(id = R.drawable.empty_diary),
            contentDescription = "Empty diaries? Time to fill them up! \n Click the + button and let the storytelling begin!",
            alpha = 0.7f
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.DiaryEntryItem(
    modifier: Modifier = Modifier,
    entry: Diary,
    onClick: (Diary) -> Unit
) {
    Card(
        modifier = modifier
            .animateItemPlacement(),
        shape = RoundedCornerShape(20.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .clickable { onClick(entry) }
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.width(8.dp))
                Text(
                    entry.title,
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (entry.content.isNotBlank()){
                Text(
                    entry.content
                )
                Spacer(Modifier.height(8.dp))
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = toDate(entry.createdDate).toString(),
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

fun toDate(dateLong: Long?): Date? {
    return dateLong?.let { Date(it) }
}