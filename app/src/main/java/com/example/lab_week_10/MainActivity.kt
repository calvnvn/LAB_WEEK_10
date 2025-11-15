package com.example.lab_week_10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDatabase
import com.example.lab_week_10.database.TotalObject
import com.example.lab_week_10.viewmodels.TotalViewModel
import java.util.Date

class MainActivity : AppCompatActivity() {

    private val db by lazy { prepareDatabase() }
    private val viewModel by lazy {
        ViewModelProvider(this)[TotalViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeValueFromDatabase()
        prepareViewModel()
    }


    override fun onStart() {
        super.onStart()
        val totalList = db.totalDao().getTotal(ID)
        if (totalList.isNotEmpty()) {
            val lastUpdateDate = totalList.first().total.date
            Toast.makeText(this, lastUpdateDate, Toast.LENGTH_LONG).show()
        }
    }

    override fun onPause() {
        super.onPause()

        val newTotalObject = TotalObject(
            viewModel.total.value ?: 0,
            Date().toString()
        )

        db.totalDao().update(Total(ID, newTotalObject))
    }

    private fun updateText(total: Int) {
        findViewById<TextView>(R.id.text_total).text =
            getString(R.string.text_total, total)
    }

    private fun prepareViewModel(){
        viewModel.total.observe(this, {
            updateText(it)
        })
        findViewById<Button>(R.id.button_increment).setOnClickListener {
            viewModel.incrementTotal()
        }
    }

    private fun prepareDatabase(): TotalDatabase {
        return Room.databaseBuilder(
            applicationContext,
            TotalDatabase::class.java, "total-database"
        ).allowMainThreadQueries().build()
    }

    private fun initializeValueFromDatabase() {
        val totalList = db.totalDao().getTotal(ID)
        if (totalList.isEmpty()) {
            val firstTotalObject = TotalObject(0, Date().toString()) //
            db.totalDao().insert(Total(id = ID, total = firstTotalObject))
        } else {
            val valueFromDb = totalList.first().total.value
            viewModel.setTotal(valueFromDb)
        }
    }

    companion object {
        const val ID: Long = 1
    }
}