package com.example.jetpackcrashcourse

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(

    var TaskName: String,
    var isSelected: Boolean = false,
    var date : Long,
    @PrimaryKey(autoGenerate = true)
    var Id: Int = 0,
)
