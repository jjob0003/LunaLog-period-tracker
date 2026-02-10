package com.example.a5046.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.Date

@Entity(tableName = "period")
data class Period(
    @ColumnInfo(name = "start_date")
    var startDate: Long = 0L,
    @ColumnInfo(name = "end_date")
    var endDate: Long = 0L,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
