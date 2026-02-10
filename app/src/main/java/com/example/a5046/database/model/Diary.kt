package com.example.a5046.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary")
data class Diary(
    val title: String = "",
    val content: String = "",
    @ColumnInfo(name = "created_date")
    val createdDate: Long = 0L,
    @ColumnInfo(name = "updated_date")
    val updatedDate: Long = 0L,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
