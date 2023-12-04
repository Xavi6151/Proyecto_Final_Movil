package com.davidlopez.proyectofinal_jsdfx.data

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
    suspend fun deleteImage(imageNotaEntity: ImageEntity)
    suspend fun updateImage(imageNotaEntity: ImageEntity)

    //video
    fun getAllVideos(id: Int): Flow<List<String>>
    suspend fun deleteAllVideos(id: Int)
    suspend fun insertVideo(videoNotaEntity: VideoEntity)
    suspend fun deleteVideo(videoNotaEntity: VideoEntity)
    suspend fun updateVideo(videoNotaEntity: VideoEntity)

    //audio
    fun getAllAudios(id: Int): Flow<List<String>>
    suspend fun deleteAllAudios(id: Int)
    suspend fun insertAudio(audioNotaEntity: AudioEntity)
    suspend fun deleteAudio(audioNotaEntity: AudioEntity)
    suspend fun updateAudio(audioNotaEntity: AudioEntity)
}