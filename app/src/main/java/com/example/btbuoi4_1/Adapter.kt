package com.example.btbuoi4_1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class TaskAdapter(context: Context, private val tasks: List<Task>) : ArrayAdapter<Task>(context, 0, tasks) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Kiểm tra xem view có sẵn không
        val listItem = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        // Lấy nhiệm vụ hiện tại
        val currentTask = tasks[position]

        // Thiết lập dữ liệu vào các TextView
        val taskId = listItem.findViewById<TextView>(R.id.taskID)
        val taskNameTextView = listItem.findViewById<TextView>(R.id.taskName)
        val taskDescriptionTextView = listItem.findViewById<TextView>(R.id.taskDescription)
        taskId.text="id: "+currentTask.id.toString()
        taskNameTextView.text = "name: "+currentTask.name
        taskDescriptionTextView.text ="description: "+currentTask.Description

        return listItem
    }
}