package com.davidlopez.proyectofinal_jsdfx.model
import androidx.annotation.StringRes

data class Tareas(
    @StringRes val nombre: Int,
    val descripcion: Int,
    val fechaInicio: Int,
    val fechaFinal: Int
)
