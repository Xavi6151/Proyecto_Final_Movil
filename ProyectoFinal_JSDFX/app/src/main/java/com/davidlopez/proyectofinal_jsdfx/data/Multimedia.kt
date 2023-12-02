package com.davidlopez.proyectofinal_jsdfx.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "imagenes")
data class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int =0 ,
    val idNota:Int =0 ,
    val uriImagen:String=""
)

@Entity(tableName = "videos")
data class VideoEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int =0 ,
    val idNota:Int =0 ,
    val uriVideo:String=""
)

@Entity(tableName = "audios")
data class AudioEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int =0 ,
    val idNota:Int =0 ,
    val uriAudio:String=""
)