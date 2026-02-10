package com.example.a5046.presentation.setting

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.a5046.presentation.userdata.PastOrPresentSelectableDates
import com.example.a5046.ui.theme.ForestGreen
import com.example.a5046.ui.theme.LightGreen
import com.example.a5046.ui.theme.SoftGreen
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    navController: NavHostController,
    navViewModel: NavigationViewModel
) {
    //val emailAddress = remember { mutableStateOf("") }

    val name = navViewModel.name.value
    val birthDateLong = navViewModel.birthDate.value
    val birthDate = birthDateLong?.let {
        Instant.ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }

    val birthDateString = birthDate?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                },
            )
        }
    ) { paddingValues ->
//    Box(modifier = Modifier
//        .fillMaxSize(),
//        contentAlignment = Alignment.Center)
//    {
        LazyColumn(modifier = Modifier
            .padding(20.dp)
            .padding(paddingValues)
            .fillMaxSize(),
            horizontalAlignment = Alignment.Start) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(text = "User Name : $name      ",  color = ForestGreen,
                        fontSize = 20.sp, // Set the font size
                        fontWeight = FontWeight.Bold)
                }
            }
            item{
                Spacer(modifier = Modifier.size(16.dp))
            }
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Date of Birth : ${birthDateString ?: ""}", color = ForestGreen,
                        fontSize = 20.sp, // Set the font size
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item{
                EditUserDialog(navViewModel = navViewModel)
            }

        }

        // UserDataItem(navViewModel)
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserDialog(navViewModel: NavigationViewModel) {
    val currentUserName = remember { mutableStateOf(navViewModel.userData.value?.name ?: "") }
    val currentBirthDate = remember { mutableStateOf(navViewModel.userData.value?.birthDate ?: 0L) }
    val showDialog = remember { mutableStateOf(false) }

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


    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = {
                Text("Edit Profile")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = currentUserName.value,
                        onValueChange = {
                            currentUserName.value = it
                            navViewModel.setName(currentUserName.value)},
                        label = {
                            Text("Username",
                                style = TextStyle(
                                    color = ForestGreen,
                                )
                            )
                        },
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    if (showDatePicker) {
                        DatePickerDialog(
                            onDismissRequest = {
                                showDatePicker = false
                            },
                            confirmButton = {
                                TextButton(onClick = {
                                    showDatePicker = false
                                    //selectedDateMillis!! null safety because type declared as Long? selectedDate = datePickerState.selectedDateMillis!!
                                    currentBirthDate.value = datePickerState.selectedDateMillis!!
                                    currentBirthDate.value?.let { navViewModel.setBirthDate(it) }
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

                    Spacer(modifier = Modifier.size(20.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Change D.O.B:       ",
                            style = TextStyle(
                                color = ForestGreen,
                            ),
                            modifier = Modifier.padding(start = 8.dp)
                        )

                        FilledTonalButton(
                            onClick = { showDatePicker = true },
                            colors = ButtonDefaults.buttonColors(containerColor = LightGreen),
                            modifier = Modifier.width(120.dp)
                        ) {
                            Text("Date", style = MaterialTheme.typography.bodyLarge)
                        }
                    }

//
//                    FilledTonalButton(
//                        onClick = {showDatePicker = true},
//                        colors = ButtonDefaults.buttonColors(containerColor = LightGreen),
//                        modifier = Modifier.width(120.dp)
//                    ) {
//                        Text("Date", style =
//                        MaterialTheme.typography.bodyLarge)
//                    }
                }
            },


            confirmButton = {
                Button(
                    onClick = {
                        navViewModel.updateUserInfo(currentUserName.value, currentBirthDate.value)
                        showDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog.value = false },
                    colors = ButtonDefaults.buttonColors(containerColor = SoftGreen)
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    FilledTonalButton(
        onClick = { showDialog.value = true },
        colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)) {
        Text("Edit")
    }
}
