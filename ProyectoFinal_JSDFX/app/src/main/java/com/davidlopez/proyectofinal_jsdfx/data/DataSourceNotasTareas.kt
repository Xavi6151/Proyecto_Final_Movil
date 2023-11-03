package com.davidlopez.proyectofinal_jsdfx.data

import com.davidlopez.proyectofinal_jsdfx.R
import com.davidlopez.proyectofinal_jsdfx.model.Content
import com.davidlopez.proyectofinal_jsdfx.model.Notas

object DataSourceNotasTareas {
    val listaNotas = listOf(
        Notas(R.string.titulo_nota,R.string.descripcion,R.string.fecha),
        Notas(R.string.titulo_nota,R.string.descripcion,R.string.fecha),
        Notas(R.string.titulo_nota,R.string.descripcion,R.string.fecha),
        Notas(R.string.titulo_nota,R.string.descripcion,R.string.fecha)
    )

    val listaTareas = listOf(
        Notas(R.string.titulo_tarea,R.string.descripcion,R.string.fecha),
        Notas(R.string.titulo_tarea,R.string.descripcion,R.string.fecha),
        Notas(R.string.titulo_tarea,R.string.descripcion,R.string.fecha),
        Notas(R.string.titulo_tarea,R.string.descripcion,R.string.fecha)
    )

    val texto = listOf(
        Content(R.string.escribirTexto)
    )
}