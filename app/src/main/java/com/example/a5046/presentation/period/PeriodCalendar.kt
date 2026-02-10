package com.example.a5046.presentation.period

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.a5046.R
import com.example.a5046.database.DateConverter
import com.example.a5046.database.model.Period
import com.example.a5046.presentation.Routes
import com.example.a5046.presentation.moodtracker.MoodScreen
import com.example.a5046.ui.theme.ForestGreen
import com.example.a5046.ui.theme.LightGreen
import com.example.a5046.ui.theme.SoftGreen
import com.kizitonwose.calendar.compose.CalendarLayoutInfo
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.TextStyle
import java.util.Locale
import java.util.TimeZone

@Composable
fun PeriodCalendar(navController: NavHostController,adjacentMonths: Long = 500, viewModels: PeriodViewModel,) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(adjacentMonths) }
    val endMonth = remember { currentMonth.plusMonths(adjacentMonths) }
    val daysOfWeek = remember { daysOfWeek() }

    val selections = remember { mutableStateListOf<CalendarDay>() }
    val dateRangesList by viewModels.allPeriods.observeAsState(initial = emptyList())

    var openDialog by rememberSaveable { mutableStateOf(false) }

    var averagePeriodLength = viewModels.calculateAveragePeriodLength()
    var averageCycleLength = viewModels.calculateAverageCycleLength()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {

        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first(),
        )

        val coroutineScope = rememberCoroutineScope()
        val visibleMonth = rememberFirstMostVisibleMonth(state, viewportPercent = 90f)

        // Calendar Title
        SimpleCalendarTitle(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
            currentMonth = visibleMonth.yearMonth,
            goToPrevious = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                }
            },
            goToNext = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                }
            },
        )

        val previoudSelections = remember { mutableStateListOf<CalendarDay>() }
        val predictionPeriods = remember { mutableStateListOf<CalendarDay>() }
        fun convertPreviousSelection() {
            previoudSelections.clear()
            dateRangesList.map { it ->
                previoudSelections.add(
                    CalendarDay(
                        date = toLocalDate(it.startDate),
                        DayPosition.MonthDate
                    )
                )
                if (toLocalDate(it.endDate).isAfter(LocalDate.of(1970, 1, 1))) {
                    val duration = Duration.between(
                        toLocalDate(it.startDate).atStartOfDay(),
                        toLocalDate(it.endDate).atStartOfDay()
                    ).toDays().toInt()
                    val date = toLocalDate(it.startDate)
                    for (i in 0..duration) {
                        previoudSelections.add(
                            CalendarDay(
                                date = date.plusDays(i.toLong()),
                                DayPosition.MonthDate
                            )
                        )
                    }
                }
            }
            var sortedRecords = previoudSelections.sortedBy { it -> it.date.toEpochDay().toInt() }

            predictionPeriods.clear()

            if(averageCycleLength != 0)
                for(i in 1..averagePeriodLength){
                    predictionPeriods.add(
                        CalendarDay(
                        date = sortedRecords.last().date.plusDays(averageCycleLength + i.toLong()),
                        DayPosition.MonthDate
                    ))
                }
            Log.i("inn", averageCycleLength.toString())
        }

        var showPeriodDeletion = remember { mutableStateOf(false) }
        var deletePeriodDate = remember { mutableStateOf(LocalDate.now()) }
        LaunchedEffect(dateRangesList){
            convertPreviousSelection()
        }

        // Calendar body
        HorizontalCalendar(
            modifier = Modifier.testTag("Calendar"),
            state = state,
            dayContent = { day ->
                Day(
                    day,
                    isSelected = selections.contains(day),
                    isPeriousRecord = previoudSelections.contains(day),
                    isPrediction = predictionPeriods.contains(day),
                ) { clicked ->
                    if (previoudSelections.contains(clicked)) {
                        showPeriodDeletion.value = true
                        deletePeriodDate.value = clicked.date
                    }
                    if (selections.contains(clicked)) {
                        selections.remove(clicked)
                        showPeriodDeletion.value = false
                    } else {
                        selections.add(clicked)
                    }

                }
            },
            monthHeader = {
                MonthHeader(daysOfWeek = daysOfWeek)
            },
        )

        fun checkUpdatedPeriod(start: LocalDate, end: LocalDate): Period {
            var newStartDate = start
            var newEndDate = end
            var id = 0

            dateRangesList.map { it ->
                val previoudStartDate = toLocalDate(it.startDate)
                val previoudEndDate = toLocalDate(it.endDate)
                if(start.isEqual(previoudStartDate) || start.isEqual(previoudEndDate) // new period record start date is equal to previous records' start date or end date
                    ||
                    (start.isAfter(previoudStartDate) && start.isBefore(previoudEndDate))
                    ||
                    (previoudEndDate.plusDays(1).isEqual(start))// in between of previous date
                )
                {
                    newStartDate = previoudStartDate
                    id = it.id
                }
                if(end.isEqual(previoudStartDate)
                    ||
                    (end.isAfter(previoudStartDate) && end.isBefore(previoudEndDate))
                    ||
                    end.plusDays(1) == previoudStartDate)
                {
                    newEndDate = previoudEndDate
                    id = it.id
                }
            }
            return Period(startDate = fromLocalDate(newStartDate),
                endDate = fromLocalDate(newEndDate),
                id = id
            )
        }

        var startDate = remember { mutableStateOf(LocalDate.now().plusDays(1)) }
        var endDate = remember { mutableStateOf(LocalDate.of(1970, 1, 1)) }


        Column {
            if(predictionPeriods.size > 0)
            {
                Text("Your next period will start from ${predictionPeriods.first().date}",
                    color = ForestGreen,
                    style =
                    MaterialTheme.typography.bodyLarge)
            }

            if(selections.isNotEmpty())
                FilledTonalButton(
                    onClick = {
                        selections.map { it ->
                            if (it.date.isBefore(startDate.value))
                                startDate.value = it.date
                            if (it.date.isAfter(endDate.value))
                                endDate.value = it.date
                        }

                        val entry = Period(
                            startDate = fromLocalDate(startDate.value),
                            endDate = fromLocalDate(endDate.value)
                        )

                        val updatedEntry = checkUpdatedPeriod(startDate.value, endDate.value)
                        if(updatedEntry.startDate == entry.startDate && updatedEntry.endDate == entry.endDate)
                        {
                            viewModels.addPeriod(entry)
                        }else{
                            viewModels.updatePeriod(updatedEntry)
                        }

                        convertPreviousSelection()
                        openDialog = true

                        // reset
                        selections.clear()
                        startDate.value = (LocalDate.now().plusDays(1))
                        endDate.value = LocalDate.of(1970, 1, 1)
                    },
                    colors = buttonColors(containerColor = ForestGreen)
                ) {
                    androidx.compose.material3.Text(
                        "Save", style =
                        MaterialTheme.typography.bodyLarge
                    )
                }

            // Delete All period record -> for testing
//            FilledTonalButton(
//                onClick = {
//                    viewModel.deleteAll()
//                },
//                colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)) {
//                androidx.compose.material3.Text("Delete All", style =
//                MaterialTheme.typography.bodyLarge)
//            }
            Spacer(modifier = Modifier.height(12.dp))
            if (showPeriodDeletion.value) {
                FilledTonalButton(
                    onClick = {
                        viewModels.deleteSpecificPeriod(fromLocalDate(deletePeriodDate.value))
                        showPeriodDeletion.value = false
                        convertPreviousSelection()
                        selections.clear()
                    },
                    colors = buttonColors(containerColor = ForestGreen)
                ) {
                    androidx.compose.material3.Text(
                        "Delete record", style =
                        MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }



        if (openDialog)
            AlertDialog(
                shape = RoundedCornerShape(25.dp),
                onDismissRequest = { openDialog = false },
                title = { Text("Next Step") },
                text = {
                    Text("Your period has been successfully recorded :) \n\n" +
                            "Do you wanna record your mood today?")

                },
                confirmButton = {
                    Button(
                        colors = buttonColors(ForestGreen),
                        shape = RoundedCornerShape(25.dp),
                        onClick = {
                            openDialog = false
                            navController.navigate(
                                route = Routes.Mood.value,
                            )
                        },
                    ) {
                        Text(
                            "Record",
                            color = Color.White
                        )
                    }
                },
                dismissButton = {
                    Button(
                        colors = buttonColors(SoftGreen),
                        shape = RoundedCornerShape(25.dp),
                        onClick = {
                            openDialog = false
                        }) {
                        Text(
                            "Cancel",
                            color = Color.White
                        )
                    }
                }
            )
    }
    }


fun fromLocalDate(localDate: LocalDate?): Long {
    return localDate!!.toEpochDay()
}

@Composable
private fun MonthHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("MonthHeader"),
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                text = dayOfWeek.displayText(),
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

fun DayOfWeek.displayText(uppercase: Boolean = false): String {
    return getDisplayName(TextStyle.SHORT, Locale.ENGLISH).let { value ->
        if (uppercase) value.uppercase(Locale.ENGLISH) else value
    }
}

@Composable
private fun Day(day: CalendarDay, isSelected: Boolean, isPeriousRecord: Boolean, isPrediction:Boolean, onClick: (CalendarDay) -> Unit) {
    val isDateBeforeToday = day.date.isBefore(LocalDate.now().plusDays(1))
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .testTag("MonthDay")
            .padding(6.dp)
            .clip(CircleShape)
            .background(color = if (isSelected) colorResource(R.color.purple_700) else if (isPeriousRecord) SoftGreen else if (isPrediction) LightGreen else Color.Transparent)
            // Disable clicks on inDates/outDates
            .clickable(
                enabled = day.position == DayPosition.MonthDate && isDateBeforeToday,
                showRipple = !isSelected,
                onClick = { onClick(day) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        val textColor = when (day.position) {
            // Color.Unspecified will use the default text color from the current theme
            DayPosition.MonthDate -> if (isSelected || isPeriousRecord || isPrediction) Color.White  else Color.Unspecified
            DayPosition.InDate, DayPosition.OutDate -> colorResource(R.color.purple_700)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text =
                day.date.dayOfMonth.toString(),
                color = if (isDateBeforeToday) textColor else Color.LightGray  ,
                fontSize = 14.sp,
            )
            if(isPeriousRecord || isPrediction)
                Image(
                    painter = painterResource(R.drawable.blood),
                    contentDescription = "",
                    modifier = Modifier
                        .size(14.dp)
                        .weight(1f)
                )
        }
    }
}


@Composable
fun rememberFirstMostVisibleMonth(
    state: CalendarState,
    viewportPercent: Float = 50f,
): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.firstMostVisibleMonth(viewportPercent) }
            .filterNotNull()
            .collect { month -> visibleMonth.value = month }
    }
    return visibleMonth.value
}

private fun CalendarLayoutInfo.firstMostVisibleMonth(viewportPercent: Float = 50f): CalendarMonth? {
    return if (visibleMonthsInfo.isEmpty()) {
        null
    } else {
        val viewportSize = (viewportEndOffset + viewportStartOffset) * viewportPercent / 100f
        visibleMonthsInfo.firstOrNull { itemInfo ->
            if (itemInfo.offset < 0) {
                itemInfo.offset + itemInfo.size >= viewportSize
            } else {
                itemInfo.size - itemInfo.offset >= viewportSize
            }
        }?.month
    }
}


@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.clickable(
    enabled: Boolean = true,
    showRipple: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
): Modifier = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = if (showRipple) LocalIndication.current else null,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = onClick,
    )
}

@Composable
fun SimpleCalendarTitle(
    modifier: Modifier,
    currentMonth: YearMonth,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
) {
    Row(
        modifier = modifier.height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CalendarNavigationIcon(
            icon = painterResource(id = R.drawable.ic_chevron_left),
            contentDescription = "Previous",
            onClick = goToPrevious,
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .testTag("MonthTitle"),
            text = currentMonth.displayText(),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
        )
        CalendarNavigationIcon(
            icon = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = "Next",
            onClick = goToNext,
        )
    }
}

@Composable
private fun CalendarNavigationIcon(
    icon: Painter,
    contentDescription: String,
    onClick: () -> Unit,
) = Box(
    modifier = Modifier
        .fillMaxHeight()
        .aspectRatio(1f)
        .clip(shape = CircleShape)
        .clickable(role = Role.Button, onClick = onClick),
) {
    Icon(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .align(Alignment.Center),
        painter = icon,
        contentDescription = contentDescription,
    )
}


fun YearMonth.displayText(short: Boolean = false): String {
    return "${this.month.displayText(short = short)} ${this.year}"
}

fun Month.displayText(short: Boolean = true): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return getDisplayName(style, Locale.ENGLISH)
}

fun toLocalDate(dateLong: Long): LocalDate {
    return dateLong.let { LocalDate.ofEpochDay(it) }
}
