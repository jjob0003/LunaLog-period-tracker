package com.example.a5046.presentation.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController



@Composable
fun ThemePage(navController: NavHostController) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        ThemeOption(
            text = "Green",
            onClick = {},
            isSelected = false
        )
        ThemeOption(
            text = "Purple",
            onClick = {},
            isSelected = false
        )
//        Button(onClick = {navController.navigate(Route.Setting)}) {
//            Text(text = "Navigate Back to Setting")
//        }
    }
}


@Composable
fun ThemeOption(text: String, onClick: () -> Unit, isSelected: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(text = text, modifier = Modifier.weight(1f))
        Checkbox(checked = isSelected, onCheckedChange = null) // Replace null with actual handler
    }
}