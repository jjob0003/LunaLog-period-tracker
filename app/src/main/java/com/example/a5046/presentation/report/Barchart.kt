package com.example.a5046.presentation.report
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a5046.presentation.period.PeriodViewModel
import com.example.a5046.ui.theme.barColors
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.example.a5046.presentation.period.toLocalDate
@Composable
fun BarChartScreen() {
    val periodViewModel: PeriodViewModel = viewModel()
    val periodsWithCycles by periodViewModel.periodsWithCycles.observeAsState(initial = emptyList())
    Log.d("BarChartScreen", "Periods with Cycles: $periodsWithCycles")
    val monthLabels = mutableListOf<String>()
    val barEntries = mutableListOf<BarEntry>()

    periodsWithCycles.forEachIndexed { index, periodWithCycle ->
        val startMonth =periodViewModel.getMonthName(toLocalDate(periodWithCycle.period.startDate))
        monthLabels.add(startMonth)
        barEntries.add(BarEntry(index.toFloat(), periodWithCycle.cycleLength?.toFloat() ?: 0f))
    }



    val barDataSet = BarDataSet(barEntries, "Period Lengths").apply {
        colors  = barColors.map { it.toArgb() }
    }
    val barData = BarData(barDataSet).apply {
        barWidth = 0.9f
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(300.dp)
            .border(1.dp, Color.Black),
        elevation = 5.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column {
            Text(
                "Period length",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp, bottom = 8.dp)
            )
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                factory = { context ->
                    BarChart(context).apply {
                        data = barData
                        description.isEnabled = false
                        setFitBars(true)
                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            valueFormatter = IndexAxisValueFormatter(monthLabels)
                            setDrawGridLines(false)
                        }

                        axisLeft.setDrawGridLines(false)
                        xAxis.setDrawGridLines(false)
                        animateY(2000)
                        legend.isEnabled = false
                    }
                }
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}
@Preview
@Composable
fun BarChartScreenPreview() {
    BarChartScreen()
}