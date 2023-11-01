package com.davidlopez.proyectofinal_jsdfx.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class NoteEditViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var noteUiState by mutableStateOf(NoteUiState())
        private set

    private fun validateInput(uiState: NoteDetails = noteUiState.noteDetails): Boolean {
        return with(uiState) {
            titulo.isNotBlank() && contenido.isNotBlank() && fecha.isNotBlank()
        }
    }
}