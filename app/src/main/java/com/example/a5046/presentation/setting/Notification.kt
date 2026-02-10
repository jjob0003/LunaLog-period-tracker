package com.example.a5046.presentation.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.a5046.ui.theme.ForestGreen


@Composable
fun NotificationPage(navController: NavHostController) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        NotificationOption(
            text = "Notify about the start of the period",
            onClick = {},
            isSelected = false,
        )
        NotificationOption(
            text = "Notify about the start of ovulation",
            onClick = {},
            isSelected = false
        )
//        Button(onClick = {navController.navigate(Route.Setting)}) {
//            Text(text = "Navigate Back to Setting")
//        }
    }
}


@Composable
fun NotificationOption(text: String, onClick: () -> Unit, isSelected: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(text = text, modifier = Modifier.weight(1f), color = ForestGreen)
        Checkbox(checked = isSelected, onCheckedChange = null,) // Replace null with actual handler
    }
}
