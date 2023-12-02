package com.davidlopez.proyectofinal_jsdfx.playback

import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun stop()
}