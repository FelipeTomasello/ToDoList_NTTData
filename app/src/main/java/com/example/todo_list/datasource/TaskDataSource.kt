package com.example.todo_list.datasource

import com.example.todo_list.model.Task

object TaskDataSource {
    private val list = arrayListOf<Task>()

    fun getList() = list.toList()

    fun insertTask(task: Task) {
        list.add(task.copy(id = list.size + 1))
    }

    fun findById(taskId: Int) {
        list.find { it.id == taskId }

    }
}