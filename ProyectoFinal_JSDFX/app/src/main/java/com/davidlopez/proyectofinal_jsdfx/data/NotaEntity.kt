package com.davidlopez.proyectofinal_jsdfx.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notas")
class NotaEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val titulo:String,
    val contenido:String,
    val fecha:String
)