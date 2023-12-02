package com.davidlopez.proyectofinal_jsdfx.record

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}