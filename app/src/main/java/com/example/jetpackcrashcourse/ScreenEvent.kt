package com.example.jetpackcrashcourse

sealed interface ScreenEvent {
    data class setTaskName(val task: String) : ScreenEvent
    data class isDone(val id : Int,val sts: Boolean) : ScreenEvent
    data class deleted(val tk: Task) : ScreenEvent
    object save : ScreenEvent
    object  sort : ScreenEvent
}