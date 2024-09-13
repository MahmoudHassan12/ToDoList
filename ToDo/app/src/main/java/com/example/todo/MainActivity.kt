package com.example.todo

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.utils.setupDialog


import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var itemList: MutableList<UserData> = mutableListOf()
    private lateinit var userDataDatabase: UserDataDatabase
    private val addTaskDialog: Dialog by lazy {
        Dialog(this, R.style.CustomDialogTheme).apply {
            setupDialog(R.layout.dialog_fragment)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        userDataDatabase = UserDataDatabase.getDatabase(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = MyAdapter(itemList) { userData ->
            deleteUser(userData)
        }
        updateRecyclerView()
        val closeButton = addTaskDialog.findViewById<ImageView>(R.id.closeImg)
        closeButton.setOnClickListener {
            addTaskDialog.dismiss()
        }
        binding.add.setOnClickListener {
            addTaskDialog.show()
        }
        addTaskDialog.setOnShowListener {

            val saveBtn = addTaskDialog.findViewById<Button>(R.id.save)
            val titleEditText = addTaskDialog.findViewById<EditText>(R.id.titleT)
            val descriptionEditText = addTaskDialog.findViewById<EditText>(R.id.ToDoT)
            val dateEditText = addTaskDialog.findViewById<EditText>(R.id.DateT)

            saveBtn?.setOnClickListener {
                var title = titleEditText?.text.toString()
                var description = descriptionEditText?.text.toString()
                var date = dateEditText?.text.toString()
                if (title.isEmpty() || description.isEmpty() || date.isEmpty()) {
                    // Show an error message
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                } else {
                    addDataInList(title, description, date)
                    addTaskDialog.dismiss()

                }
            }

        }
    }

    fun addDataInList(title: String, description: String, date: String) {
        val userData = UserData(title = title, description = description, date = date)
        lifecycleScope.launch {
            try {
                userDataDatabase.userDataDao().insertUserData(userData)
                updateRecyclerView()
            } catch (e: Exception) {
                Log.e("MainActivity", "Error adding data: ${e.message}")
            }
        }
    }


    fun updateRecyclerView() {
        lifecycleScope.launch {
            val dataFromRoom = userDataDatabase.userDataDao().getAllUserData()
            itemList.clear()
            itemList.addAll(dataFromRoom)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    fun deleteUser(userData: UserData) {
        lifecycleScope.launch {
            userDataDatabase.userDataDao().deleteUserData(userData)
            val position = itemList.indexOf(userData)
            if (position != -1) {
                (binding.recyclerView.adapter as MyAdapter).removeItem(position)
            }
        }
    }
}