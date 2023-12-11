package com.example.jetpackcrashcourse

data class TaskState(
    val id: Int = 0,
    val Tasks: List<Task> = emptyList(),
    val Task_Name: String = "",
    val sts: Boolean? = null,
    val sortbyDate : Boolean = true

    )
