package com.example.a5046.database

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoField
import java.util.Date


public class DateConverter {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }
//
//    @TypeConverter
//    fun fromDate(date: Date?): Long? {
//        return if (date == null) null else date.getTime()
//    }

    @TypeConverter
    fun fromLocalDate(localDate: LocalDate?): Long {
        return localDate!!.toEpochDay()
    }

    @TypeConverter
    fun toLocalDate(dateLong: Long): LocalDate? {
        return dateLong.let { LocalDate.ofEpochDay(it) }
    }
}