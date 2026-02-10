package com.example.a5046.components

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.a5046.ui.theme.ForestGreen
import com.example.a5046.ui.theme.SoftGreen

@Composable
fun ActiveButton(buttonText: String){
    FilledTonalButton(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)) {
        Text(buttonText, style =
        MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun InactiveButton(buttonText: String){
    FilledTonalButton(onClick = { }, colors = ButtonDefaults.buttonColors(containerColor = SoftGreen)) {
        Text(buttonText, style =
        MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
fun LunaLogButtonsPreview() {
//    InactiveButton("I am inactive")
    ActiveButton("I am active")
}