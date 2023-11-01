package com.davidlopez.proyectofinal_jsdfx.data

import android.content.Context

interface AppContainer{
    val notesRepository: NotesRepository
}

class AppDataContainer(private val context: Context) : AppContainer{
    override val notesRepository: NotesRepository by lazy {
        OfflineNotesRepository(NotesDatabase.getDatabase(context).notaDAO())
    }
}