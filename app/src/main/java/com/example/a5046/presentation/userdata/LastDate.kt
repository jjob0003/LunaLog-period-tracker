package com.example.a5046.presentation.userdata


import com.example.a5046.presentation.setting.NavigationViewModel
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.a5046.presentation.Routes
import com.example.a5046.ui.theme.ForestGreen
import com.example.a5046.ui.theme.LightGreen
import com.example.a5046.ui.theme.SoftGreen
import com.example.a5046.ui.theme.WarmGreen
import java.time.Instant
import java.util.Calendar


@RequiresApi(0)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LastDate(
    navController: NavHostController,
    navViewModel: NavigationViewModel
) {
    val lastDate = remember { mutableStateOf<Long?>(null) }
    val calendar = Calendar.getInstance()
    calendar.set(2024, 0, 1) // month (0) is January
//    val datePickerState = rememberDatePickerState(
//        initialSelectedDateMillis = Instant.now().toEpochMilli()
//    )
    val datePickerState =  rememberDatePickerState(
        selectableDates = PastOrPresentSelectableDates
    )

    var showDatePicker by remember {
        mutableStateOf(false)
    }
    var selectedDate by remember {
        mutableStateOf(calendar.timeInMillis)
    }

    Column(modifier = Modifier.padding(80.dp)) {
        Text(
            text = "When did your period last start?",
            color = ForestGreen,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Record your last period or skip if you do not know.",
            style = MaterialTheme.typography.bodyMedium,
            color = ForestGreen
        )
        Spacer(modifier = Modifier.height(35.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = {
                        showDatePicker = false
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            showDatePicker = false
                            lastDate.value = datePickerState.selectedDateMillis
                            lastDate.value?.let { navViewModel.setLastDate(it) }
                        }) {
                            Text(text = "OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDatePicker = false
                        }) {
                            Text(text = "Cancel")
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState
                    )
                }
            }// end of if
            FilledTonalButton(
                onClick = { showDatePicker = true },
                colors = ButtonDefaults.buttonColors(containerColor = LightGreen),
                modifier = Modifier.width(120.dp)
            ) {
                Text("Date", style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(modifier = Modifier.height(200.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            FilledTonalButton(
                onClick = { navController.navigate(Routes.PeriodLength.value) },
                colors = ButtonDefaults.buttonColors(containerColor = SoftGreen)
            ) {
                Text("Skip", style = MaterialTheme.typography.bodyLarge)
            }

            FilledTonalButton(
                onClick = { navController.navigate(Routes.PeriodLength.value) },
                colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
            ) {
                Text("Next", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }

}