package com.davidlopez.proyectofinal_jsdfx.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NotaEntity::class, ImageEntity::class, VideoEntity::class], version = 3, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notaDAO(): NotaDAO

    companion object {
        @Volatile
        private var Instance: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, NotesDatabase::class.java, "database_notes")
                    .build().also { Instance = it }
            }
        }
    }
}