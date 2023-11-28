package com.davidlopez.proyectofinal_jsdfx.sizeScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun rememberWindowInfo(): WindowInfo{
    val configuration = LocalConfiguration.current
    return WindowInfo(
        screenWindthInfo = when{
            configuration.screenWidthDp < 600 -> WindowInfo.WindowType.Compact
            configuration.screenWidthDp < 840 -> WindowInfo.WindowType.Medium
            else -> WindowInfo.WindowType.Expanded
        },
        screenHeighInfo =when{
            configuration.screenHeightDp < 400 -> WindowInfo.WindowType.Compact
            configuration.screenHeightDp < 900 -> WindowInfo.WindowType.Medium
            else -> WindowInfo.WindowType.Expanded
        },
        screenWidth = configuration.screenWidthDp.dp,
        screenHeight = configuration.screenHeightDp.dp
    )
}
//Clase para detectar los tipos de ventana
data class WindowInfo(
    val screenWindthInfo: WindowType,
    val screenHeighInfo: WindowType,
    val screenWidth: Dp,
    val screenHeight: Dp

){
    sealed class WindowType{
        object Compact: WindowType() //Ventanas pequeñas (telefonos en modo vertical)
        object Medium: WindowType() //Ventana para tabletas pequeñas o telefono en modo horizontal
        object  Expanded: WindowType()  //Ventana para tables grandes
    }
}