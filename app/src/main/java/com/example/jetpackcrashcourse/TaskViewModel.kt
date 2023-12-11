package com.example.jetpackcrashcourse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel(private val dao: TaskDao) : ViewModel() {

    private val _state = MutableStateFlow(TaskState())
    private var _dateSorted = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    private var taskData =
        _dateSorted.flatMapLatest { sort ->
            if (sort) {
                dao.getNotesOrderdByDateAdded()
            } else {
                dao.getNotesOrderdByTitle()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    val state = combine(_state, _dateSorted, taskData) { s, D, t ->
        s.copy(
            Tasks = t
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskState())

    fun onEvent(event: ScreenEvent) {
        when (event) {
            is ScreenEvent.setTaskName -> {
                _state.update {
                    it.copy(
                        Task_Name = event.task
                    )
                }
            }

            is ScreenEvent.deleted -> {
                viewModelScope.launch {
                    dao.DeleteData(event.tk)
                }
            }

            is ScreenEvent.isDone -> {
                viewModelScope.launch {
                    dao.updateTask(id = event.id, sts = event.sts)
                }
            }

            ScreenEvent.save -> {
                val taskname = state.value.Task_Name
                val entry = Task(TaskName = taskname, date = System.currentTimeMillis())
                viewModelScope.launch {
                    dao.insertData(entry)
                }
                _state.update {
                    it.copy(
                        Task_Name = ""
                    )
                }
            }

            ScreenEvent.sort -> run {
                _dateSorted.value = !_dateSorted.value
                _state.update {
                    it.copy(
                        sortbyDate = !_dateSorted.value
                    )
                }
            }

        }
    }
}