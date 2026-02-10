package com.example.a5046.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userData")
data class UserData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val name: String,
    val birthDate: Long?,
    val lastDate: Long?,
    val periodLength: String,
    val cycleLength: String
)