package com.davidlopez.proyectofinal_jsdfx.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidlopez.proyectofinal_jsdfx.data.NotaEntity
import com.davidlopez.proyectofinal_jsdfx.data.NotesRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.TimeZone

class NoteEditViewModel(
    private val notesRepository: NotesRepository
) : ViewModel() {

    var noteUiState by mutableStateOf(NoteUiState())
        private set

    fun updateUiStateEdit(noteDetails: NoteDetails) {
        noteUiState =
            NoteUiState(noteDetails = noteDetails, isEntryValid = validateInput(noteDetails))
    }

    suspend fun updateNote() {
        if (validateInput()) {
            notesRepository.updateNote(noteUiState.noteDetails.toNote())
        }
    }

    private fun validateInput(uiState: NoteDetails = noteUiState.noteDetails): Boolean {
        return with(uiState) {
            titulo.isNotBlank() && contenido.isNotBlank() && fecha.isNotBlank()
        }
    }

    var textoCuerpo by mutableStateOf("")
    var textoTitulo by mutableStateOf("")
    var opcion by mutableStateOf("")
    var expandidoTipo by mutableStateOf(false)
    var ordenado by mutableStateOf("")
    var expandidoOrden by mutableStateOf(false)

    fun actualizarTextoCuerpo(text: String){
        textoCuerpo=text
    }

    fun actualizarTextoTitulo(text: String){
        textoTitulo=text
    }

    fun actualizarOpcion(text: String){
        opcion=text
    }

    fun actualizarExpandidoTipo(boolean: Boolean){
        expandidoTipo=boolean
    }

    fun actualizarOrdenado(text: String){
        ordenado=text
    }

    fun actualizarExpandidoOrden(boolean: Boolean){
        expandidoOrden=boolean
    }

}