package com.davidlopez.proyectofinal_jsdfx.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class NoteDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * UI state for ItemDetailsScreen
 */
data class NoteDetailsUiState(
    val outOfStock: Boolean = true,
    val noteDetails: NoteDetails = NoteDetails()
)