package com.example.btbuoi4_1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: TaskDatabaseHelper
    private lateinit var listView: ListView
    private lateinit var but:Button
    private lateinit var butd:Button
    private lateinit var butcls:Button
    private val taskList = ArrayList<Task>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        but = findViewById(R.id.button)
        butd=findViewById(R.id.butDel)
        butcls=findViewById(R.id.butClearData)
        butAdd(but)
        butDel(butd)
        butCLRData(butcls)
        listView = findViewById(R.id.listView)
        dbHelper = TaskDatabaseHelper(this)

        try {
            dbHelper.createDatabase()

            loadTasks()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun butAdd(but:Button){
        but.setOnClickListener(){
            val nam=findViewById<EditText>(R.id.editTextname).text.toString()
            val desc=findViewById<EditText>(R.id.editTextDescription).text.toString()
            val task=Task(dbHelper.coutColumn()+1,nam,desc)
            dbHelper.addTask(task)
            loadTasks()
        }
    }
    private fun clsEtext(){
        findViewById<EditText>(R.id.editTextid).text.clear()
        findViewById<EditText>(R.id.editTextname).text.clear()
        findViewById<EditText>(R.id.editTextDescription).text.clear()
    }
    private fun butDel(but:Button){
        but.setOnClickListener(){
            var id=findViewById<EditText>(R.id.editTextid).text.toString().toInt()
            dbHelper.deleteTask(id)
            loadTasks()
        }
    }
    private fun butCLRData(but:Button){
        but.setOnClickListener(){
            dbHelper.deleteData()
            loadTasks()
        }
    }
    private fun loadTasks() {
        clsEtext()
        taskList.clear()
        taskList.addAll(dbHelper.getAllTasks())

        val adapter = TaskAdapter(this,  taskList)
        listView.adapter = adapter
    }

}
