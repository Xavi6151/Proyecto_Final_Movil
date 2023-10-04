package com.davidlopez.proyectofinal_jsdfx.data

import com.davidlopez.proyectofinal_jsdfx.R
import com.davidlopez.proyectofinal_jsdfx.model.Notas
import com.davidlopez.proyectofinal_jsdfx.model.Tareas

object DataSourceNotasTareas {
    val listaNotas = listOf(
        Notas(R.string.titulo_nota,R.string.descripcion,R.string.fecha),
        Notas(R.string.titulo_nota,R.string.descripcion,R.string.fecha)
    )

    val listaTareas = listOf(
        Tareas(R.string.titulo_nota,R.string.descripcion,R.string.fecha, R.string.fecha2),
        Tareas(R.string.titulo_nota,R.string.descripcion,R.string.fecha, R.string.fecha2)
    )
}