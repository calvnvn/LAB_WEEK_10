package com.example.lab_week_10.viewmodels // Pastikan package-nya benar

import androidx.lifecycle.ViewModel

class TotalViewModel : ViewModel() {
    // [1] Variabel 'total' sekarang 'pindah rumah' ke sini
    var total: Int = 0

    // [2] Fungsi untuk menambah 'total'
    fun incrementTotal(): Int {
        total++
        return total
    }
}