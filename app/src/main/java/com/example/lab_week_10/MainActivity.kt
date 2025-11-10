package com.example.lab_week_10 // Sesuaikan package Anda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider // Import ini penting
import com.example.lab_week_10.viewmodels.TotalViewModel // Import ViewModel Anda

class MainActivity : AppCompatActivity() {

    // [1] Membuat instance ViewModel. Ini sudah benar.
    private val viewModel by lazy {
        ViewModelProvider(this)[TotalViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // [2] Saat Activity dibuat, ambil nilai 'total' dari ViewModel.
        // Jika baru dibuka, nilainya 0. Jika habis rotasi,
        // nilainya akan tetap (misal: 1, 2, dst).
        updateText(viewModel.total)

        // [3] Atur listener untuk tombol
        findViewById<Button>(R.id.button_increment).setOnClickListener {
            // [4] Panggil fungsi increment di ViewModel
            val newTotal = viewModel.incrementTotal()

            // [5] Perbarui UI secara manual dengan nilai baru
            updateText(newTotal)
        }
    }

    // Fungsi ini tidak berubah
    private fun updateText(total: Int) {
        findViewById<TextView>(R.id.text_total).text =
            getString(R.string.text_total, total)
    }

    // Kita hapus fungsi 'prepareViewModel()' yang salah dari modul
}