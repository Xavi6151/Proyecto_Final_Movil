package com.davidlopez.proyectofinal_jsdfx.data

import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    /*** Retornar todas las notas */
    fun getAllNotesStream(): Flow<List<NotaEntity>>

    /*** Retornar nota de acuerdo al id */
    fun getNoteStream(id: Int): Flow<NotaEntity?>

    /*** Insertar nota */
    suspend fun insertNote(notaEntity: NotaEntity)

    /*** Eliminar nota */
    suspend fun deleteNote(notaEntity: NotaEntity)

    /*** Actualizar nota */
    suspend fun updateNote(notaEntity: NotaEntity)
}