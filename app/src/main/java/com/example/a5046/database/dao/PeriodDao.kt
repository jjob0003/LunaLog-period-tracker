package com.example.a5046.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.a5046.database.model.Period
import kotlinx.coroutines.flow.Flow

@Dao
interface PeriodDao {

    @Query("SELECT * FROM period")
    fun getAllPeriods(): LiveData<List<Period>>

    @Query("SELECT * FROM period WHERE id = :id")
    suspend fun getPeriod(id: Int): Period

    @Insert
    suspend fun insertPeriod(period: Period)

    @Update
    suspend fun updatePeriod(period: Period)

    @Delete
    suspend fun deletePeriod(period: Period)

    @Query("DELETE FROM period WHERE :date BETWEEN start_date AND end_date")
    suspend fun deleteSpecificPeriod(date: Long)

    @Query("DELETE FROM period")
    suspend fun deleteAll()

}
