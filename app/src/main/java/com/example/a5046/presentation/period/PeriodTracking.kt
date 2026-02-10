package com.example.a5046.presentation.period

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PeriodTracker(navController: NavHostController, viewModel: PeriodViewModel){
    PeriodCalendar(navController, viewModels = viewModel)
}
