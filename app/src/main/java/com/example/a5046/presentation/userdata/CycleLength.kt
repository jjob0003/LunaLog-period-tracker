package com.example.a5046.presentation.userdata
import com.example.a5046.presentation.setting.NavigationViewModel
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.a5046.database.model.Period
import com.example.a5046.database.model.UserData
import com.example.a5046.presentation.Routes
import com.example.a5046.presentation.period.PeriodViewModel
import com.example.a5046.ui.theme.ForestGreen
import com.example.a5046.ui.theme.SoftGreen
import com.example.a5046.ui.theme.WarmGreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CycleLength(
    navController: NavHostController,
    navViewModel: NavigationViewModel,
    periodViewModel: PeriodViewModel
) {
    val cycleLength = remember {mutableStateOf("")}
    val states =
        listOf("", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35")
    var isExpanded by remember { mutableStateOf(false) }
    var selectedState by remember { mutableStateOf(states[0]) }


    Column(modifier = Modifier.padding(80.dp)) {
        Text(
            text = "How long is your average cycle length?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = ForestGreen
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "This information will help us make more accurate predictions.",
            style = MaterialTheme.typography.bodyMedium,
            color = WarmGreen
        )
        Spacer(modifier = Modifier.height(35.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it },
                modifier = Modifier
                    .weight(1f)
                    .background(color = Color.Transparent),
            ) {
                TextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .focusProperties {
                            canFocus = false
                        }
                        .padding(bottom = 8.dp)
                        ,
                    readOnly = true,
                    value = selectedState,
                    onValueChange = {},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                )
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                )
                {
                    states.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                selectedState = selectionOption
                                cycleLength.value = selectionOption
                                isExpanded = false
                                navViewModel.setCycleLength(cycleLength.value)
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }

            Text(
                text = "days",
                modifier = Modifier.padding(start = 8.dp),
                color = ForestGreen
            )

        }


        Spacer(modifier = Modifier.height(200.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            FilledTonalButton(
                onClick = {
                    val userData = UserData(
                        1,
                        name = navViewModel.name.value ?: "",
                        birthDate = navViewModel.birthDate.value,
                        lastDate = navViewModel.lastDate.value,
                        periodLength = navViewModel.periodLength.value ?: "0",
                        cycleLength = navViewModel.cycleLength.value ?: "0"
                    )
                    navViewModel.insertUserData(userData)

                    if (navViewModel.lastDate.value == null || navViewModel.periodLength.value?.toIntOrNull() == 0)
                    {
//                        val entry = Period(0L, 0L)
//                        periodViewModel.addPeriod(entry)
                    }
                    else
                    {
                        val entry = Period(
                                startDate = navViewModel.lastDate.value!! / 86400000,
                                endDate = navViewModel.lastDate.value!! / 86400000 - 1
                                        + (navViewModel.periodLength.value?.toIntOrNull() ?: 0)
                        )

                        periodViewModel.addPeriod(entry)
                    }
                    navController.navigate(Routes.Main.value)
                },
                colors = ButtonDefaults.buttonColors(containerColor = SoftGreen)
            ) {
                Text("Skip", style = MaterialTheme.typography.bodyLarge)
            }

            FilledTonalButton(
                onClick = {
                    val userData = UserData(
                        1,
                        name = navViewModel.name.value ?: "",
                        birthDate = navViewModel.birthDate.value,
                        lastDate = navViewModel.lastDate.value,
                        periodLength = navViewModel.periodLength.value ?: "0",
                        cycleLength = navViewModel.cycleLength.value ?: "0"
                    )
                    navViewModel.insertUserData(userData)

                    if (navViewModel.lastDate.value == null || navViewModel.periodLength.value?.toIntOrNull() == 0)
                    {
//                        val entry = Period(0L, 0L)
//                        periodViewModel.addPeriod(entry)
                    }
                    else
                    {
                        val entry = Period(
                            startDate = navViewModel.lastDate.value!! / 86400000,
                            endDate = navViewModel.lastDate.value!! / 86400000 - 1
                                    + (navViewModel.periodLength.value?.toIntOrNull() ?: 0)
                        )
                        periodViewModel.addPeriod(entry)
                    }
                    navController.navigate(Routes.Main.value)
                },
                colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
            ) {
                Text("Next", style = MaterialTheme.typography.bodyLarge)
            }
        }



    }
}

