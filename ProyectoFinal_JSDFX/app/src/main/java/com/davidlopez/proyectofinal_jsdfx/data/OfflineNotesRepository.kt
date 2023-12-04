package com.davidlopez.proyectofinal_jsdfx.data

import kotlinx.coroutines.flow.Flow

class OfflineNotesRepository(private val notaDAO: NotaDAO) : NotesRepository {
    override fun getAllNotesStream(): Flow<List<NotaEntity>> = notaDAO.getAllItems()
    override fun getNoteStream(id: Int): Flow<NotaEntity?> = notaDAO.getItem(id)
    override suspend fun insertNote(item: NotaEntity):Long = notaDAO.insert(item)
    override suspend fun deleteNote(item: NotaEntity) = notaDAO.delete(item)
    override suspend fun updateNote(item: NotaEntity) = notaDAO.update(item)
    //imagenes
    override fun getAllImages(id: Int): Flow<List<String>> = notaDAO.getAllImages(id)
    override suspend fun deleteAllImages(id: Int)= notaDAO.deleteAllImages(id)
    override suspend fun insertImage(imageNotaEntity: ImageEntity) = notaDAO.insert(imageNotaEntity)
    override suspend fun deleteImage(imageNotaEntity: ImageEntity) = notaDAO.delete(imageNotaEntity)
    override suspend fun updateImage(imageNotaEntity: ImageEntity) = notaDAO.update(imageNotaEntity)
    //videos
    override fun getAllVideos(id: Int): Flow<List<String>> = notaDAO.getAllVideos(id)
    override suspend fun deleteAllVideos(id: Int)= notaDAO.deleteAllVideos(id)
    override suspend fun insertVideo(videoNotaEntity: VideoEntity) = notaDAO.insert(videoNotaEntity)
    override suspend fun deleteVideo(videoNotaEntity: VideoEntity) = notaDAO.delete(videoNotaEntity)
    override suspend fun updateVideo(videoNotaEntity: VideoEntity) = notaDAO.update(videoNotaEntity)
    //audios
    override fun getAllAudios(id: Int): Flow<List<String>> = notaDAO.getAllAudios(id)
    override suspend fun deleteAllAudios(id: Int)= notaDAO.deleteAllAudios(id)
    override suspend fun insertAudio(audioNotaEntity: AudioEntity) = notaDAO.insert(audioNotaEntity)
    override suspend fun deleteAudio(audioNotaEntity: AudioEntity) = notaDAO.delete(audioNotaEntity)
    override suspend fun updateAudio(audioNotaEntity: AudioEntity) = notaDAO.update(audioNotaEntity)
}
