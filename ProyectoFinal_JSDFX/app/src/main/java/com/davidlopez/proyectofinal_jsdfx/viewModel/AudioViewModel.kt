package com.davidlopez.proyectofinal_jsdfx.viewModel

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.AndroidViewModel
import com.davidlopez.proyectofinal_jsdfx.playback.AndroidAudioPlayer
import com.davidlopez.proyectofinal_jsdfx.record.AndroidAudioRecorder
import java.io.File

class AudioViewModel(application: Application) : AndroidViewModel(application) {
    val recorder by lazy { AndroidAudioRecorder(application.applicationContext) }
    val player by lazy { AndroidAudioPlayer(application.applicationContext) }
    var audioFile: File? = null
}