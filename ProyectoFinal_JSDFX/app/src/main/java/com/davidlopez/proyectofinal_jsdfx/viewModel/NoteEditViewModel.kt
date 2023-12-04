package com.davidlopez.proyectofinal_jsdfx.viewModel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidlopez.proyectofinal_jsdfx.data.AudioEntity
import com.davidlopez.proyectofinal_jsdfx.data.ImageEntity
import com.davidlopez.proyectofinal_jsdfx.data.NotaEntity
import com.davidlopez.proyectofinal_jsdfx.data.NotesRepository
import com.davidlopez.proyectofinal_jsdfx.data.VideoEntity
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.TimeZone
import kotlin.math.log

class NoteEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository
) : ViewModel() {

    var noteUiState by mutableStateOf(NoteUiStateEditar())
        private set

    fun updateUiStateEdit(noteDetails: NoteDetailsEditar) {
        noteUiState =
            NoteUiStateEditar(noteDetails = noteDetails, isEntryValid = validateInput(noteDetails))
    }
    suspend fun updateNote() {
        if (validateInput()) {
            notesRepository.updateNote(noteUiState.noteDetails.toNoteEdit())
            notesRepository.upTraImagenes(urislist, itemId)
            notesRepository.upTraVideos(urisVideolist, itemId)
        }
    }
    private fun validateInput(uiState: NoteDetailsEditar = noteUiState.noteDetails): Boolean {
        return with(uiState) {
            titulo.isNotBlank() && contenido.isNotBlank() && fecha.isNotBlank()
        }
    }

    var opcion by mutableStateOf("")
    var expandidoTipo by mutableStateOf(false)

    fun actualizarOpcion(text: String){ opcion=text }
    fun actualizarExpandidoTipo(boolean: Boolean){ expandidoTipo=boolean }

    private val itemId: Int = checkNotNull(savedStateHandle["id"])
    var urislist= mutableStateListOf<Uri?>()
    var urisVideolist= mutableStateListOf<Uri?>()
    var urisAudiolist= mutableStateListOf<Uri?>()
    init {
        viewModelScope.launch {
            noteUiState = notesRepository.getNoteStream(itemId)
                .filterNotNull()
                .first()
                .toNoteUiStateEditar(true)
            imagenes()
            videos()
            audios()
        }
    }

    var cantidadImagenes by mutableStateOf(0)
    suspend fun imagenes(){
        urislist.clear()
        urislist.addAll(getAllImages())
        cantidadImagenes=urislist.size
    }

    var cantidadVideos by mutableStateOf(0)
    suspend fun videos(){
        urisVideolist.clear()
        urisVideolist.addAll(getAllVideos())
        cantidadVideos=urisVideolist.size
    }

    var cantidadAudios by mutableStateOf(0)
    suspend fun audios(){
        urisAudiolist.clear()
        urisAudiolist.addAll(getAllAudios())
        cantidadAudios=urisAudiolist.size
    }

    suspend fun getAllImages(): SnapshotStateList<Uri?> {
        var lista=notesRepository.getAllImages(itemId).first()
        var lista2= mutableStateListOf<Uri?>()
        for((indice) in lista.withIndex()){
            lista2.add(Uri.parse(lista[indice]))
        }
        return lista2
    }

    suspend fun getAllVideos():SnapshotStateList<Uri>{
        var lista=notesRepository.getAllVideos(itemId).first()
        var lista2= mutableStateListOf<Uri>()
        for((indice) in lista.withIndex()){
            lista2.add(Uri.parse(lista[indice]))
        }
        return lista2
    }

    suspend fun getAllAudios():SnapshotStateList<Uri>{
        var lista=notesRepository.getAllAudios(itemId).first()
        var lista2= mutableStateListOf<Uri>()
        for((indice) in lista.withIndex()){
            lista2.add(Uri.parse(lista[indice]))
        }
        return lista2
    }

    var notificacion by mutableStateOf(false)
    var hora by mutableStateOf("")
    var hour by mutableStateOf(0)
    var minute by mutableStateOf(0)
    var uriMostrar by mutableStateOf(Uri.EMPTY)
    var showReloj by mutableStateOf(false)
    var showOption by mutableStateOf(false)
    var calcular by mutableStateOf(false)
    var recordatorio by mutableStateOf(false)
    var isEditar by mutableStateOf(true)

    fun updateShowReloj(boolean: Boolean){ showReloj= boolean }
    fun updateRecordatorio(boolean: Boolean){ recordatorio= boolean }
    fun updateShowOption(boolean: Boolean){ showOption= boolean }
    fun updateCalcular(boolean: Boolean){ calcular= boolean }
    fun updateNotificacion(boolean: Boolean){ notificacion=boolean }
    fun updateHora(string: String){ hora=string }
    fun updateHour(int: Int){ hour=int }
    fun updateMinuto(int: Int){ minute=int }
    fun updateUriMostrar(uri: Uri?){ uriMostrar=uri }

    //imagen
    var hasImage by mutableStateOf(false)
    var mostrarImagen by mutableStateOf(false)
    var imageUri by mutableStateOf<Uri?>(null)

    fun updatehasImage(boolean: Boolean){ hasImage= boolean }
    fun updateImageUri(uri: Uri?){
        imageUri=uri
    }
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
    var videoUri by mutableStateOf<Uri?>(null)

    fun updatehasVideo(boolean: Boolean){ hasVideo= boolean }
    fun updateVideoUri(uri: Uri?){
        videoUri=uri
    }
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
    var hasAudio by mutableStateOf(false)
    var mostrarAudio by mutableStateOf(false)
    var fileNumb by mutableStateOf(0)

    fun updateFileNumb(int: Int){ fileNumb=int }
    fun updateMostrarAudio(boolean: Boolean){ mostrarAudio= boolean }
    fun deleteLastUriAudios(){
        urisAudiolist.removeLast()
        cantidadAudios=urisAudiolist.size
    }
}

data class  NoteUiStateEditar(
    val noteDetails: NoteDetailsEditar = NoteDetailsEditar(),
    val isEntryValid: Boolean = false
)

data class NoteDetailsEditar(
    val id: Int = 0,
    val titulo: String = "",
    val contenido: String = "",
    val fecha: String = "" + Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City")).get(Calendar.DAY_OF_MONTH)+
            "/"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"/"+Calendar.getInstance().get(Calendar.YEAR)
)

fun NoteDetailsEditar.toNoteEdit(): NotaEntity = NotaEntity(
    id = id,
    titulo = titulo,
    contenido = contenido,
    fecha = fecha
)

fun NotaEntity.toNoteUiStateEditar(isEntryValid: Boolean = false): NoteUiStateEditar = NoteUiStateEditar(
    noteDetails = this.toNoteDetailsEditar(),
    isEntryValid = isEntryValid
)

fun NotaEntity.toNoteDetailsEditar(): NoteDetailsEditar = NoteDetailsEditar(
    id = id,
    titulo = titulo,
    contenido = contenido,
    fecha = fecha
)