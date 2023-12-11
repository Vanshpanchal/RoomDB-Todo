package com.example.jetpackcrashcourse

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Upsert
    suspend fun insertData(tk: Task)

    @Delete
    suspend fun DeleteData(tk: Task)

    @Query("SELECT * FROM task ORDER BY date DESC")
    fun getNotesOrderdByDateAdded(): Flow<List<Task>>


    @Query("SELECT * FROM task ORDER BY  TaskName ASC")
    fun getNotesOrderdByTitle(): Flow<List<Task>>

    @Query("UPDATE task SET isSelected = :sts  WHERE Id = :id")
    suspend fun updateTask(id: Int, sts: Boolean)
}