package com.example.jetpackcrashcourse

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 2)
abstract class Taskdb : RoomDatabase() {
abstract val dao : TaskDao
}