package com.davidlopez.proyectofinal_jsdfx.viewModel

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.davidlopez.proyectofinal_jsdfx.InventoryApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            NoteEditViewModel(inventoryApplication().container.notesRepository)
        }
        // Initializer for ItemEntryViewModel
        initializer {
            NoteEntryViewModel(inventoryApplication().container.notesRepository)
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(inventoryApplication().container.notesRepository)
        }
    }
}

fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)