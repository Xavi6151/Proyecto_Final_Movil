package com.davidlopez.proyectofinal_jsdfx.data

import android.net.Uri
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDAO {
    @Insert
    suspend fun insert(notaEntity: NotaEntity):Long
    @Update
    suspend fun update(notaEntity: NotaEntity)
    @Delete
    suspend fun delete(notaEntity: NotaEntity)
    @Query("SELECT * FROM Notas WHERE id = :id")
    fun getItem(id: Int): Flow<NotaEntity>
    @Query("SELECT * FROM Notas ORDER BY fecha ASC")
    fun getAllItems(): Flow<List<NotaEntity>>

    //IMAGENES
    @Query("SELECT uriImagen from imagenes WHERE idNota= :id")
    fun getAllImages(id: Int): Flow<List<String>>
    @Query("DELETE from imagenes WHERE idNota= :id")
    suspend fun deleteAllImages(id: Int)
    @Insert
    suspend fun insert(imageNotaEntity: ImageEntity)
    @Transaction
    suspend fun upTraImagenes(listaUris: List<Uri?>, id: Int){
        deleteAllImages(id)
        listaUris.forEach{uri->
            var imageNota = ImageEntity(0, id,""+uri)
            insert(imageNota)
        }
    }

    //VIDEOS
    @Query("SELECT uriVideo from videos WHERE idNota= :id")
    fun getAllVideos(id: Int): Flow<List<String>>
    @Query("DELETE from videos WHERE idNota= :id")
    suspend fun deleteAllVideos(id: Int)
    @Insert
    suspend fun insert(videoEntity: VideoEntity)
    @Transaction
    suspend fun upTraVideos(listaUris: List<Uri?>, id: Int){
        deleteAllVideos(id)
        listaUris.forEach{uri->
            var videoNota = VideoEntity(0, id,""+uri)
            insert(videoNota)
        }
    }

    //AUDIOS
    @Query("SELECT uriAudio from audios WHERE idNota= :id")
    fun getAllAudios(id: Int): Flow<List<String>>
    @Insert
    suspend fun insert(audioEntity: AudioEntity)

}