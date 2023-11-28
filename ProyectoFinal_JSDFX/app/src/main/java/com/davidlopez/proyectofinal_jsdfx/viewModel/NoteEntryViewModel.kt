package com.davidlopez.proyectofinal_jsdfx.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.davidlopez.proyectofinal_jsdfx.R
import com.davidlopez.proyectofinal_jsdfx.data.NotaEntity
import com.davidlopez.proyectofinal_jsdfx.data.NotesRepository
import java.util.Calendar
import java.util.TimeZone

class NoteEntryViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    var noteUiState by mutableStateOf(NoteUiState())
        private set

    fun updateUiState(noteDetails: NoteDetails) {
        noteUiState =
            NoteUiState(noteDetails = noteDetails, isEntryValid = validateInput(noteDetails))
    }

    suspend fun saveNote() {
        if (validateInput()) {
            notesRepository.insertNote(noteUiState.noteDetails.toNote())
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

data class  NoteUiState(
    val noteDetails: NoteDetails = NoteDetails(),
    val isEntryValid: Boolean = false
)

data class NoteDetails(
    val id: Int = 0,
    val titulo: String = "",
    val contenido: String = "",
    val fecha: String = "" + Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City")).get(Calendar.DAY_OF_MONTH)+
            "/"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"/"+Calendar.getInstance().get(Calendar.YEAR)
)

fun NoteDetails.toNote(): NotaEntity = NotaEntity(
    id = id,
    titulo = titulo,
    contenido = contenido,
    fecha = fecha
)

fun NotaEntity.toNoteUiState(isEntryValid: Boolean = false): NoteUiState = NoteUiState(
    noteDetails = this.toNoteDetails(),
    isEntryValid = isEntryValid
)

fun NotaEntity.toNoteDetails(): NoteDetails = NoteDetails(
    id = id,
    titulo = titulo,
    contenido = contenido,
    fecha = fecha
)