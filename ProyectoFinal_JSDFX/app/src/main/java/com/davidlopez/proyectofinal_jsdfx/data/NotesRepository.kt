package com.davidlopez.proyectofinal_jsdfx.data

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    /*** Retornar todas las notas */
    fun getAllNotesStream(): Flow<List<NotaEntity>>
    /*** Retornar nota de acuerdo al id */
    fun getNoteStream(id: Int): Flow<NotaEntity?>
    /*** Insertar nota */
    suspend fun insertNote(notaEntity: NotaEntity):Long
    /*** Eliminar nota */
    suspend fun deleteNote(notaEntity: NotaEntity)
    /*** Actualizar nota */
    suspend fun updateNote(notaEntity: NotaEntity)

    //imagen
    fun getAllImages(id: Int): Flow<List<String>>
    suspend fun deleteAllImages(id: Int)
    suspend fun insertImage(imageNotaEntity: ImageEntity)
    suspend fun upTraImagenes(listaUris: List<Uri?>, id: Int)

    //video
    fun getAllVideos(id: Int): Flow<List<String>>
    suspend fun deleteAllVideos(id: Int)
    suspend fun insertVideo(videoNotaEntity: VideoEntity)
    suspend fun upTraVideos(listaUris: List<Uri?>, id: Int)

    //audio
    fun getAllAudios(id: Int): Flow<List<String>>
    suspend fun insertAudio(audioNotaEntity: AudioEntity)
}