package com.example.a5046.database.repositry

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.a5046.database.LunaLogDatabase
import com.example.a5046.database.dao.PeriodDao
import com.example.a5046.database.model.Period
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit




class PeriodRepositry (application: Application) {
    private var periodDao: PeriodDao =
        LunaLogDatabase.getDatabase(application).periodDAO()

    val allPeriods: LiveData<List<Period>> = periodDao.getAllPeriods()

    fun getAllEntries(): LiveData<List<Period>> {
        return periodDao.getAllPeriods()
    }

    suspend fun insert(period: Period) {
        periodDao.insertPeriod(period)
    }

    suspend fun delete(period: Period) {
        periodDao.deletePeriod(period)
    }

    suspend fun deleteSpecificPeriod(date: Long) {
        periodDao.deleteSpecificPeriod(date)
    }

    suspend fun update(period: Period) {
        Log.i("update", period.toString())
        periodDao.updatePeriod(period)
    }

    suspend fun deleteALl() {
        periodDao.deleteAll()
    }


    fun getAllPeriodsWithCycles(): LiveData<List<PeriodWithCycle>> {
        val result = MediatorLiveData<List<PeriodWithCycle>>()
        result.addSource(allPeriods) { periods ->
            result.value = periods.map { period ->
                PeriodWithCycle(period, calculateCycleLength(period))
            }
        }
        return result
    }

    private fun calculateCycleLength(period: Period): Long? {
        if (period.startDate > 0 && period.endDate > 0) {
            val startLocalDate = toLocalDate(period.startDate)
            val endLocalDate = toLocalDate(period.endDate)

            Log.d("BarChartScreen", "$startLocalDate , $endLocalDate")

            return countDaysBetween(startLocalDate, endLocalDate)
        }
        return null
    }

    private fun countDaysBetween(start: LocalDate, end: LocalDate): Long {
        var daysCount = 0L
        var currentDate = start
        while (currentDate.isBefore(end)) {
            currentDate = currentDate.plusDays(1)
            daysCount++
        }
        Log.d("BarChartScreen", "Days count , $daysCount")
        return daysCount + 1
    }
}

data class PeriodWithCycle(
    val period: Period,
    val cycleLength: Long?
)

fun fromLocalDate(localDate: LocalDate?): Long {
    return localDate!!.toEpochDay()
}


fun toLocalDate(dateLong: Long): LocalDate {
    return dateLong.let { LocalDate.ofEpochDay(it) }
}