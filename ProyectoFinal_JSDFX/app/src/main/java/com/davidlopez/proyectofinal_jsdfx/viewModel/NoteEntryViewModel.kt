package com.davidlopez.proyectofinal_jsdfx.viewModel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.davidlopez.proyectofinal_jsdfx.R
import com.davidlopez.proyectofinal_jsdfx.data.ImageEntity
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
            var id=notesRepository.insertNote(noteUiState.noteDetails.toNote())
            saveImages(id)
            //saveVideos(id)
        }
    }

    suspend fun saveImages(id:Long){
        urislist.forEach{uri->
            var imageNota= ImageEntity(0, id.toInt(),""+uri)
            notesRepository.insertImage(imageNota)
        }
    }

    private fun validateInput(uiState: NoteDetails = noteUiState.noteDetails): Boolean {
        return with(uiState) {
            titulo.isNotBlank() && contenido.isNotBlank() && fecha.isNotBlank()
        }
    }

    var opcion by mutableStateOf("")
    var expandidoTipo by mutableStateOf(false)
    var notificacion by mutableStateOf(false)
    var hora by mutableStateOf("")
    var hour by mutableStateOf(0)
    var minute by mutableStateOf(0)
    var uriMostrar by mutableStateOf(Uri.EMPTY)
    var showReloj by mutableStateOf(false)
    var showOption by mutableStateOf(false)
    var calcular by mutableStateOf(false)
    var recordatorio by mutableStateOf(false)

    fun updateShowReloj(boolean: Boolean){ showReloj= boolean }

    fun updateRecordatorio(boolean: Boolean){ recordatorio= boolean }

    fun updateShowOption(boolean: Boolean){ showOption= boolean }

    fun updateCalcular(boolean: Boolean){ calcular= boolean }

    fun actualizarOpcion(text: String){ opcion=text }

    fun actualizarExpandidoTipo(boolean: Boolean){ expandidoTipo=boolean }

    fun updateNotificacion(boolean: Boolean){ notificacion=boolean }

    fun updateHora(string: String){ hora=string }

    fun updateHour(int: Int){ hour=int }

    fun updateMinuto(int: Int){ minute=int }

    fun updateUriMostrar(uri: Uri?){ uriMostrar=uri }

    //imagen
    var hasImage by mutableStateOf(false)
    var mostrarImagen by mutableStateOf(false)
    var cantidadImagenes by mutableStateOf(0)
    var imageUri by mutableStateOf<Uri?>(null)
    var urislist= mutableStateListOf<Uri?>()

    fun updatehasImage(boolean: Boolean){ hasImage= boolean }

    fun updateUrisList(uri: Uri?){
        urislist.add(uri)
        cantidadImagenes=urislist.size
    }

    fun deleteLastUri(){
        urislist.removeLast()
        cantidadImagenes=urislist.size
    }

    fun updateMostrarImagen(boolean: Boolean){ mostrarImagen= boolean }

    //videos
    var hasVideo by mutableStateOf(false)
    var mostrarVideo by mutableStateOf(false)
    var cantidadVideos by mutableStateOf(0)
    var videoUri by mutableStateOf<Uri?>(null)
    var urisVideolist= mutableStateListOf<Uri?>()

    fun updatehasVideo(boolean: Boolean){ hasVideo= boolean }

    fun updateUrisVideoList(uri: Uri?){
        urisVideolist.add(uri)
        cantidadVideos=urisVideolist.size
    }

    fun deleteLastVideoUri(){
        urisVideolist.removeLast()
        cantidadVideos=urisVideolist.size
    }

    fun updateMostrarVideo(boolean: Boolean){ mostrarVideo= boolean }
    //audios
    val listaAudios = mutableStateListOf<Uri>()
    var cantidadAudios by mutableStateOf(0)
    var urisAudioslist= mutableStateListOf<Uri?>()
    var showAudio by mutableStateOf(false)

    fun updateShowAudio(boolean: Boolean){ showAudio= boolean }

    fun updateUrisAudiosList(uri: Uri){
        urisAudioslist.add(uri)
        cantidadAudios=urisAudioslist.size
    }

    fun deleteLastUriAudios(){
        urisAudioslist.removeLast()
        cantidadAudios=urisAudioslist.size
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