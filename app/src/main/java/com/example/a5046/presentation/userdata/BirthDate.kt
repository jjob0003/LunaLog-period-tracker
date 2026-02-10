package com.example.a5046.presentation.userdata


import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.a5046.presentation.Routes
import com.example.a5046.presentation.setting.NavigationViewModel
import com.example.a5046.ui.theme.ForestGreen
import com.example.a5046.ui.theme.LightGreen
import com.example.a5046.ui.theme.SoftGreen
import java.time.Instant
import java.util.Calendar


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthDate(
    navController: NavHostController,
    navViewModel: NavigationViewModel
) {
    val birthDate = remember { mutableStateOf<Long?>(null) }
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "",
//                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
//                        color = ForestGreen,
//                        fontSize = 20.sp, // Set the font size
//                        fontWeight = FontWeight.Bold
                    )
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(200.dp))

            Text(
                text = "What is your date of birth?",
                color = ForestGreen,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = {
                        showDatePicker = false
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            showDatePicker = false
                            //selectedDateMillis!! null safety because type declared as Long? selectedDate = datePickerState.selectedDateMillis!!
                            birthDate.value = datePickerState.selectedDateMillis
                            birthDate.value?.let { navViewModel.setBirthDate(it) }
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
                )

                {
                    DatePicker(
                        state = datePickerState
                    )
                }
            }

            Spacer(modifier = Modifier.size(35.dp))

            FilledTonalButton(
                onClick = {showDatePicker = true},
                colors = ButtonDefaults.buttonColors(containerColor = LightGreen),
                modifier = Modifier.width(120.dp)
            ) {
                Text("Date", style =
                MaterialTheme.typography.bodyLarge)
            }

//            Button(
//                onClick = {
//                    showDatePicker = true
//                },
//                modifier = Modifier
//                    .padding(16.dp)
//                    .height(48.dp)
//                    .width(100.dp),
//                contentPadding = PaddingValues(12.dp)
//            ) {
//                Text(text = "Date")
//            }


            Spacer(modifier = Modifier.size(100.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(60.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledTonalButton(onClick = {navController.navigate(Routes.LastDate.value)}, colors = ButtonDefaults.buttonColors(containerColor = SoftGreen)) {
                    Text("Skip", style =
                    MaterialTheme.typography.bodyLarge)
                }
                FilledTonalButton(onClick = {navController.navigate(Routes.LastDate.value)}, colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)) {
                    Text("Next", style =
                    MaterialTheme.typography.bodyLarge)
                }

            }

        }
    }
}

