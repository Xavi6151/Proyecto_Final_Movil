package com.davidlopez.proyectofinal_jsdfx.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDAO {
    @Insert
    suspend fun insert(notaEntity: NotaEntity)
    @Update
    suspend fun update(notaEntity: NotaEntity)
    @Delete
    suspend fun delete(notaEntity: NotaEntity)

    @Query("SELECT * FROM Notas WHERE id = :id")
    fun getItem(id: Int): Flow<NotaEntity>

    @Query("SELECT * FROM Notas ORDER BY fecha ASC")
    fun getAllItems(): Flow<List<NotaEntity>>

}