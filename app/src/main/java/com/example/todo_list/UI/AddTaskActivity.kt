package com.example.todo_list.UI

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.namespace.databinding.ActivityAddTaskBinding
import com.example.todo_list.datasource.TaskDataSource
import com.example.todo_list.extensions.format
import com.example.todo_list.extensions.text
import com.example.todo_list.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*


class AddTaskActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra(TASK_ID)) {
            val taskId = intent.getIntExtra(TASK_ID,0)
            TaskDataSource.findById(taskId)?.let { 
                binding.tilTitle.text = it.,
                binding.tilDate.text.date
            }

        }
        insertListeners()
    }

    private fun insertListeners() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offSet = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offSet).format()
            }
            datePicker.show(supportFragmentManager,"DatePickerTag")
        }

        binding.tilTime.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()
            timePicker.addOnPositiveButtonClickListener {
                val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.tilTime.text = "$hour:$minute"

            }
            timePicker.show(supportFragmentManager,null)
        }

        binding.btnCreate.setOnClickListener {
            val task = Task(
                title = binding.tilTitle.text ,
                date = binding.tilDate.text,
                hour = binding.tilTime.text ,
                description = binding.tilDescription.text
            )
                TaskDataSource.insertTask(task)
                setResult(Activity.RESULT_OK)
                finish()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }
    companion object {
        const val TASK_ID = "task_id"
    }
}