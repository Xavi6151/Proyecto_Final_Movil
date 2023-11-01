package com.davidlopez.proyectofinal_jsdfx

import android.app.Application
import com.davidlopez.proyectofinal_jsdfx.data.AppContainer
import com.davidlopez.proyectofinal_jsdfx.data.AppDataContainer

class InventoryApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}