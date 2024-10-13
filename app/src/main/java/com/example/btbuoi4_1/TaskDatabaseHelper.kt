package com.example.btbuoi4_1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import kotlin.coroutines.coroutineContext

class TaskDatabaseHelper(private val context: Context) : SQLiteOpenHelper(context, "BTbuoi4.db", null, 1) {

    private val dbPath: String = context.getDatabasePath("BTbuoi4.db").path

    override fun onCreate(db: SQLiteDatabase) {
        // Bảng đã có trong cơ sở dữ liệu từ assets
        db.execSQL("CREATE TABLE IF NOT EXISTS tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS tasks")
        onCreate(db)
    }

    fun createDatabase() {
        if (!checkDatabase()) {
            this.writableDatabase
            this.close()
            copyDatabase()
        }
    }

    private fun checkDatabase(): Boolean {
        var checkDB: SQLiteDatabase? = null
        try {
            checkDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY)
        } catch (e: Exception) {
            // Database doesn't exist
        }
        checkDB?.close()
        return checkDB != null
    }

    @Throws(IOException::class)
    private fun copyDatabase() {
        val input: InputStream = context.assets.open("BTbuoi4.db")
        val output: OutputStream = FileOutputStream(dbPath)
        val buffer = ByteArray(1024)
        var length: Int
        while (input.read(buffer).also { length = it } > 0) {
            output.write(buffer, 0, length)
        }
        output.flush()
        output.close()
        input.close()
    }

    // CRUD operations
    fun addTask(task: Task) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("name", task.name)
        values.put("description", task.Description)
        db.insert("tasks", null, values)
        db.close()
    }
    fun coutColumn(): Int {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT COUNT(*) FROM tasks", null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        db.close()
        return count
    }
    fun getAllTasks(): List<Task> {
        val taskList = mutableListOf<Task>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM tasks", null)

        if (cursor.moveToFirst()) {
            do {
                val task = Task(
                    id = cursor.getInt(cursor.getColumnIndex("id")),
                    name = cursor.getString(cursor.getColumnIndex("name")),
                    Description = cursor.getString(cursor.getColumnIndex("Description"))
                )
                taskList.add(task)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return taskList
    }

    fun updateTask(task: Task) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("name", task.name)
        values.put("description", task.Description)
        db.update("tasks", values, "id = ?", arrayOf(task.id.toString()))
        db.close()
    }

    fun deleteTask(taskId: Int) {
        val db = this.writableDatabase
        db.delete("tasks", "id = ?", arrayOf(taskId.toString()))
        db.close()
    }
    fun deleteData(){
        val db = this.writableDatabase
        db.delete("tasks",null,null)
        db.close()
    }
}
