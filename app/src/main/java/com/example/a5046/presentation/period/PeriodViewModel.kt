package com.example.a5046.presentation.period

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.a5046.database.model.Period
import com.example.a5046.database.model.UserData
import com.example.a5046.database.repository.UserDataRepository
import com.example.a5046.database.repositry.PeriodRepositry
import com.example.a5046.database.repositry.PeriodWithCycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PeriodViewModel(application: Application) : AndroidViewModel(application)  {
    private var pRepository: PeriodRepositry

    init{
        pRepository = PeriodRepositry(application)
        pRepository.getAllEntries()
    }
    val periodsWithCycles: LiveData<List<PeriodWithCycle>> = pRepository.getAllPeriodsWithCycles()

    var allPeriods: LiveData<List<Period>> = pRepository.allPeriods
    fun addPeriod(period: Period) = viewModelScope.launch(Dispatchers.IO) {
        pRepository.insert(period)
    }
    fun updatePeriod(period: Period) = viewModelScope.launch(Dispatchers.IO) {
        pRepository.update(period)
    }
    fun deletePeriod(period: Period) = viewModelScope.launch(Dispatchers.IO) {
        pRepository.delete(period)
    }

    fun deleteSpecificPeriod(date: Long) = viewModelScope.launch(Dispatchers.IO) {
        pRepository.deleteSpecificPeriod(date)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        pRepository.deleteALl()
    }
    fun getMonthName(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern("MMM"))
    }

    fun calculateAveragePeriodLength(): Int {
        var days = 0
        var periods = 0
        allPeriods.value?.forEach{ it->
            days += (it.endDate.toInt() - it.startDate.toInt()) + 1
            periods ++
        }
        if(days == 0 || periods == 0)
            return 0
        return (days / periods)
    }

    fun calculateAverageCycleLength(): Int {
        if(allPeriods.value?.isEmpty() == true || allPeriods.value?.size == 1)
            return 0
        var daysBetween = 0
        var periods = 0

        // Sorting Period Records
        var sortedPeriods = allPeriods.value?.sortedBy { it -> it.startDate.toInt() }
        var lastEnd = sortedPeriods?.first()?.endDate?.toInt()
        sortedPeriods?.forEach{it->
            // start from second element/ period
            if(periods != 0)
                daysBetween += (it.startDate.toInt() - lastEnd!!) + 1
            periods ++
            lastEnd = it.endDate.toInt()
        }
        if(daysBetween == 0 || periods == 1)
            return 0
        return (daysBetween / periods)
    }
}
