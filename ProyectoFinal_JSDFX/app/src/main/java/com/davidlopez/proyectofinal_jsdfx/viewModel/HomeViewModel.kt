package com.davidlopez.proyectofinal_jsdfx.viewModel

import androidx.lifecycle.ViewModel
import com.davidlopez.proyectofinal_jsdfx.data.NotaEntity

class HomeViewModel() : ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(val itemList: List<NotaEntity> = listOf())