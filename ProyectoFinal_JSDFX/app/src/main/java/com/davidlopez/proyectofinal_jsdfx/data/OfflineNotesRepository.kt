package com.davidlopez.proyectofinal_jsdfx.data

import kotlinx.coroutines.flow.Flow

class OfflineNotesRepository(private val notaDAO: NotaDAO) : NotesRepository {
    override fun getAllNotesStream(): Flow<List<NotaEntity>> = notaDAO.getAllItems()
    override fun getNoteStream(id: Int): Flow<NotaEntity?> = notaDAO.getItem(id)
    override suspend fun insertNote(item: NotaEntity) = notaDAO.insert(item)
    override suspend fun deleteNote(item: NotaEntity) = notaDAO.delete(item)
    override suspend fun updateNote(item: NotaEntity) = notaDAO.update(item)
}