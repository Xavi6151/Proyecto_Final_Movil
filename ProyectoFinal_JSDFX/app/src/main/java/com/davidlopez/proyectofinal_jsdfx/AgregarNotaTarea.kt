package com.davidlopez.proyectofinal_jsdfx

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AsyncPlayer
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.davidlopez.proyectofinal_jsdfx.data.DataSourceNotasTareas
import com.davidlopez.proyectofinal_jsdfx.navigation.AppScreens
import com.davidlopez.proyectofinal_jsdfx.notification.NotificacionProgramada
import com.davidlopez.proyectofinal_jsdfx.notification.NotificacionProgramada.Companion.NOTIFICACION_ID
import com.davidlopez.proyectofinal_jsdfx.playback.AndroidAudioPlayer
import com.davidlopez.proyectofinal_jsdfx.record.AndroidAudioRecorder
import com.davidlopez.proyectofinal_jsdfx.sizeScreen.WindowInfo
import com.davidlopez.proyectofinal_jsdfx.sizeScreen.rememberWindowInfo
import com.davidlopez.proyectofinal_jsdfx.viewModel.AppViewModelProvider
import com.davidlopez.proyectofinal_jsdfx.viewModel.HomeViewModel
import com.davidlopez.proyectofinal_jsdfx.viewModel.NoteDetails
import com.davidlopez.proyectofinal_jsdfx.viewModel.NoteEditViewModel
import com.davidlopez.proyectofinal_jsdfx.viewModel.NoteEntryViewModel
import com.davidlopez.proyectofinal_jsdfx.viewModel.NoteUiState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.Calendar

@Composable
fun DespliegueAgregarNotaTarea(
    contentPadding: PaddingValues,
    viewModel: NoteEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val context= LocalContext.current
    val audioRecorder = AndroidAudioRecorder(context)
    VentanaDialogoAgregarAudio(
        show =  viewModel.showAudio ,
        onDismiss = {
            audioRecorder.stop()
            viewModel.updateShowAudio(false)
        },
        onConfirm = {
            viewModel.updateFileNumb(viewModel.fileNumb+1)
            Log.d("filename",""+viewModel.fileNumb)
            audioRecorder.start(File("dummy"),viewModel.fileNumb)
            viewModel.updateHasAudio(true)
            viewModel.updateUrisAudiosList(audioRecorder.getContentUri())
        },
        titulo = stringResource(id = R.string.audio),
        text = stringResource(id = R.string.audioTexto)
    )
    LazyColumn(
        contentPadding=contentPadding,
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))){
        items(DataSourceNotasTareas.texto){
            NoteEntryBody(
                noteUiState = viewModel.noteUiState,
                onNoteValueChange = viewModel::updateUiState,
                viewModel = viewModel)
        }
    }
}

var dato: File? = null
fun obtenerCacheDir(cacheDir: File){
    dato = cacheDir
}

@Composable
fun NoteEntryBody(
    noteUiState: NoteUiState,
    onNoteValueChange: (NoteDetails) -> Unit,
    viewModel: NoteEntryViewModel
){
    AddTextCard(
        noteDetails = noteUiState.noteDetails,
        onValueChange = onNoteValueChange,
        viewModel = viewModel
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTextCard(
    noteDetails: NoteDetails,
    onValueChange: (NoteDetails) -> Unit = {},
    viewModel: NoteEntryViewModel
){
    TextField(
        value = noteDetails.contenido,
        onValueChange = { onValueChange(noteDetails.copy(contenido = it)) },
        modifier = Modifier
            .padding(top = 4.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent),
        label = { Text(text = stringResource(id = R.string.escribirTexto)) },
        textStyle = MaterialTheme.typography.bodyMedium
    )
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.weight(0.5f)
        ) {
            verImagenes(viewModel = viewModel)
        }
        Column(
            modifier = Modifier.weight(0.5f)
        ) {
            verVideos(viewModel = viewModel)
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth()
    ){
        Column(
            modifier = Modifier.weight(1f)
        ) {
            verAudios(viewModel = viewModel)
        }
    }
}

@Composable
fun AppAgregarNotaTarea(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: NoteEntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    viewModelHome: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
)
{
    val tamanioPantalla = rememberWindowInfo()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if(tamanioPantalla.screenWindthInfo is WindowInfo.WindowType.Compact){
                parteDeArriba2Compacta(modifier = Modifier, navController = navController, viewModel = viewModel)
            }else{
                parteDeArriba2Extendida(modifier = Modifier, navController = navController, viewModel = viewModel)
            }
        },
        bottomBar = {
            Column {
                Row (
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.grande))
                        .background(Color(0, 66, 255, 255))
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    ImageCapture(viewModel = viewModel, modifier = modifier)
                    Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_medium)))

                    VideoCapture(viewModel = viewModel, modifier = modifier)
                    Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_medium)))

                    AudioCapture(viewModel = viewModel, modifier = modifier)
                    Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_medium)))

                    Recordatorio(viewModel = viewModel, modifier = Modifier)
                }
            }
        }
    ){
        DespliegueAgregarNotaTarea(contentPadding = it)
    }
}

@Composable
private fun TituloNoteEntryBody(
    noteUiState: NoteUiState,
    onNoteValueChange: (NoteDetails) -> Unit
){
    TituloNota(
        noteDetails = noteUiState.noteDetails,
        onValueChange = onNoteValueChange
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TituloNota(
    noteDetails: NoteDetails,
    onValueChange: (NoteDetails) -> Unit = {}
){
    TextField(
        value = noteDetails.titulo,
        onValueChange = {onValueChange(noteDetails.copy(titulo = it))},
        modifier = Modifier
            .padding(start = 8.dp, top = 4.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        label = { Text(text = stringResource(id = R.string.titulo)) },
        textStyle = MaterialTheme.typography.bodyLarge
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun parteDeArriba2Compacta(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: NoteEntryViewModel
){
    val coroutineScope = rememberCoroutineScope()
    Column {
        Row (
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.grande))
                .background(Color(0, 66, 255, 255))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = modifier.weight(1f)
            ) {
                TituloNoteEntryBody(noteUiState = viewModel.noteUiState, onNoteValueChange = viewModel::updateUiState)
            }
            Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_small)))
            val botonListo = LocalContext.current.applicationContext
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveNote()
                        navController.navigate(route = AppScreens.MainScreen.route)
                        Toast.makeText(botonListo, R.string.agregarAccion, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.size(
                    width = dimensionResource(R.dimen.mediano),
                    height = dimensionResource(R.dimen.grande)
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.a_adir),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(), // La imagen ocupará todo el espacio del botón
                    tint = Color.Unspecified // Puedes ajustar el color de la imagen si lo deseas
                )
            }
            Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_small)))
        }
        Row (
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.grande))
                .background(Color(0, 46, 195, 255))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            ExposedDropdownMenuBox(
                modifier = modifier
                    .padding(start = 8.dp, end = 8.dp),
                expanded = viewModel.expandidoTipo,
                onExpandedChange = {viewModel.actualizarExpandidoTipo(it)}
            ) {
                TextField(
                    value = viewModel.opcion,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(id = R.string.tipo)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                )
                ExposedDropdownMenu(
                    expanded = viewModel.expandidoTipo,
                    onDismissRequest = { viewModel.expandidoTipo=false })
                {
                    var texto1 = stringResource(id = R.string.titulo_nota)
                    var texto2 = stringResource(id = R.string.titulo_tarea)
                    DropdownMenuItem(
                        text = { Text(texto1) },
                        onClick = {
                            viewModel.actualizarOpcion(texto1)
                            viewModel.expandidoTipo=false
                            viewModel.updateRecordatorio(false)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(texto2) },
                        onClick = {
                            viewModel.actualizarOpcion(texto2)
                            viewModel.expandidoTipo=false
                            viewModel.updateRecordatorio(true)
                        }
                    )
                }
            }
            Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_smaller)))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun parteDeArriba2Extendida(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: NoteEntryViewModel
){
    val coroutineScope = rememberCoroutineScope()
    Column {
        Row (
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.grande))
                .background(Color(0, 66, 255, 255))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = modifier.weight(0.5f)
            ) {
                TituloNoteEntryBody(noteUiState = viewModel.noteUiState, onNoteValueChange = viewModel::updateUiState)
            }
            Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_smaller)))
            ExposedDropdownMenuBox(
                modifier = modifier
                    .padding(start = 8.dp, end = 8.dp),
                expanded = viewModel.expandidoTipo,
                onExpandedChange = {viewModel.actualizarExpandidoTipo(it)}
            ) {
                TextField(
                    value = viewModel.opcion,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(id = R.string.tipo)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                    modifier = Modifier
                        .menuAnchor(),
                )
                ExposedDropdownMenu(
                    expanded = viewModel.expandidoTipo,
                    onDismissRequest = { viewModel.expandidoTipo=false })
                {
                    var texto1 = stringResource(id = R.string.titulo_nota)
                    var texto2 = stringResource(id = R.string.titulo_tarea)
                    DropdownMenuItem(
                        text = { Text(texto1) },
                        onClick = {
                            viewModel.actualizarOpcion(texto1)
                            viewModel.expandidoTipo=false
                            viewModel.updateRecordatorio(false)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(texto2) },
                        onClick = {
                            viewModel.actualizarOpcion(texto2)
                            viewModel.expandidoTipo=false
                            viewModel.updateRecordatorio(true)
                        }
                    )
                }
            }
            //Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_smaller)))
            val botonListo = LocalContext.current.applicationContext
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveNote()
                        navController.navigate(route = AppScreens.MainScreen.route)
                        Toast.makeText(botonListo, R.string.agregarAccion, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.size(
                    width = dimensionResource(R.dimen.mediano),
                    height = dimensionResource(R.dimen.grande)
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.a_adir),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(), // La imagen ocupará todo el espacio del botón
                    tint = Color.Unspecified // Puedes ajustar el color de la imagen si lo deseas
                )
            }
            Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_small)))
        }
    }
}

@Composable
fun VentanaDialogoAgregarAudio(
    show:Boolean,
    onDismiss:()->Unit,
    onConfirm:()->Unit,
    titulo:String,
    text:String
){
    if(show) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text(text = stringResource(id = R.string.grabar))
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(text = stringResource(id = R.string.parar))
                }
            },
            title = { Text(titulo) },
            text = { Text(text) }
        )
    }
}

@Composable
private fun Notificaciones(
    milisegundos: Long,
    viewModel: NoteEntryViewModel
){
    if(viewModel.notificacion){
        val context= LocalContext.current
        val idCanal= "CanalNotas"

        LaunchedEffect(Unit){
            crearCanalNotificacion(idCanal,context)
        }
        notificacionProgramada(context,milisegundos)
    }
}

private fun crearCanalNotificacion(
    idCanal: String,
    context: Context
){
    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
        val nombre= "CanalNotas"
        val descripcion="Canal de notificaciones notas"
        val importancia=NotificationManager.IMPORTANCE_DEFAULT
        val canal= NotificationChannel(idCanal,nombre,importancia)
            .apply {
                description=descripcion
            }
        val notificationManager:NotificationManager=
            context.getSystemService(Context.NOTIFICATION_SERVICE) as
                    NotificationManager
        notificationManager.createNotificationChannel(canal)
    }
}

@SuppressLint("ScheduleExactAlarm")
private fun notificacionProgramada(context: Context, milisegundos:Long)
{
    val intent= Intent(context, NotificacionProgramada::class.java)
    val pendingIntent=PendingIntent.getBroadcast(
        context,
        NOTIFICACION_ID,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    var alarmManager=context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.setExact(
        AlarmManager.RTC_WAKEUP,
        Calendar.getInstance().timeInMillis+milisegundos,
        pendingIntent
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Reloj(
    viewModel: NoteEntryViewModel
){
    if(viewModel.showReloj){
        val state = rememberTimePickerState()
        DatePickerDialog(
            onDismissRequest = { viewModel.updateShowReloj(false) },
            confirmButton = {
                Button(
                    onClick = { viewModel.updateShowReloj(false)  }
                ) {
                    Text(text = stringResource(id = R.string.confirmar))
                }
            },
            dismissButton = {
                Button(
                    onClick = { viewModel.updateShowReloj(false)  }
                ) {
                    Text(text = stringResource(id = R.string.cancelar))
                }
            }
        ) {
            TimePicker(state = state, modifier = Modifier.fillMaxWidth())
        }
        val hour=state.hour
        val minut=state.minute
        hour?.let {
            minut?.let{
                viewModel.updateHora("$hour:$minut:00")
                viewModel.updateHour(hour)
                viewModel.updateMinuto(minut)
            }
        }
    }
}

@Composable
private fun opcionesRecordatorios(
    viewModel: NoteEntryViewModel,
){
    if(viewModel.showOption){
        Dialog(
            properties = DialogProperties(dismissOnClickOutside = true),
            onDismissRequest = {
                viewModel.updateShowOption(false)
                viewModel.updateCalcular(false)
            }
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(.9f)
            ) {
                Column (
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                ){
                    Row (
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Row(
                            modifier = Modifier
                                .weight(.5f)
                                .padding(2.dp)
                        ) {
                            Button(
                                onClick = {
                                    viewModel.updateShowReloj(true)},
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = stringResource(id = R.string.definirHora))
                            }
                        }
                    }
                    Text(
                        text = stringResource(id = R.string.recordatorio),
                        modifier = Modifier.padding(5.dp))
                    Text(
                        text = viewModel.hora,
                        modifier = Modifier.padding(5.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                viewModel.updateNotificacion(true)
                                viewModel.updateCalcular(true)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = stringResource(id = R.string.confirmar))
                        }
                        if(viewModel.calcular){
                            var time= LocalTime.now()
                            var time2=LocalTime.of(viewModel.hour,viewModel.minute,0)
                            val dif=time.until(time2, ChronoUnit.MILLIS)
                            if(dif>0){
                                Notificaciones(dif,viewModel)
                            }
                            viewModel.updateShowOption(false)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Recordatorio(
    viewModel: NoteEntryViewModel,
    modifier:Modifier
){
    Button(
        onClick = {
            viewModel.updateShowOption(true)
        },
        enabled=viewModel.recordatorio,
        //contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        Box{
            if(viewModel.recordatorio){
                Image(
                    painter = painterResource(id = R.drawable.notificacion),
                    contentDescription = null,
                    modifier = modifier
                        .size(
                            width = dimensionResource(R.dimen.pequeño),
                            height = dimensionResource(R.dimen.pequeño)
                        )
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            }else{
                Image(
                    painter = painterResource(id = R.drawable.notificacion),
                    contentDescription = null,
                    modifier = modifier
                        .size(
                            width = dimensionResource(R.dimen.pequeño),
                            height = dimensionResource(R.dimen.pequeño)
                        )
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            }
            Reloj(viewModel = viewModel)
            opcionesRecordatorios(viewModel = viewModel)
        }
    }
}

@Composable
private fun verImagenes(
    viewModel: NoteEntryViewModel,
    modifier:Modifier=Modifier
) {
    Row(
        modifier= modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Column {
            Column(
                modifier=modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(
                    id = R.string.cantidadImagenes)+viewModel.cantidadImagenes)
            }
            if (viewModel.hasImage){
                LazyColumn(modifier = modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(top = 4.dp)) {
                    items(viewModel.urislist.toList()) { uri ->
                        Surface(
                            onClick = {
                                viewModel.updateMostrarImagen(true)
                                viewModel.updateUriMostrar(uri)
                            },
                            modifier = modifier
                                .size(
                                    width = dimensionResource(R.dimen.masGrande),
                                    height = dimensionResource(R.dimen.masGrande)
                                )
                        ){
                            Box{
                                Image(
                                    painter = painterResource(id = R.drawable.imagen),
                                    contentDescription = null,
                                    modifier = modifier
                                        .size(
                                            width = dimensionResource(R.dimen.masGrande),
                                            height = dimensionResource(R.dimen.masGrande)
                                        )
                                        .aspectRatio(1f),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
                mostrarImagen(viewModel = viewModel, uri = viewModel.uriMostrar)
            }
            Column (
                modifier=modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    modifier=modifier.height(dimensionResource(id = R.dimen.mediano))
                ) {
                    Column(
                        modifier=modifier.weight(0.2f)
                    ) {
                        filesImageCapture(viewModel = viewModel)
                    }
                    Column(
                        modifier=modifier.weight(0.8f)
                    ) {
                        Button(
                            enabled = viewModel.cantidadImagenes!=0,
                            onClick = {
                                viewModel.deleteLastUri()
                            },
                            contentPadding = PaddingValues(10.dp)) {
                            Text(text = stringResource(id = R.string.mensajeEliminar))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun mostrarImagen(
    viewModel: NoteEntryViewModel,
    uri: Uri?
){
    if(viewModel.mostrarImagen){
        Dialog(
            properties = DialogProperties(dismissOnClickOutside = true),
            onDismissRequest = { viewModel.updateMostrarImagen(false) }
        ) {
            AsyncImage(
                model = uri,
                modifier = Modifier.fillMaxSize(.9f),
                contentDescription = "Amplied image",
            )
        }
    }
}

@Composable
private fun filesImageCapture(
    viewModel: NoteEntryViewModel
){
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            viewModel.updatehasImage(uri != null)
            if(viewModel.hasImage){
                viewModel.updateUrisList(uri)
            }
        }
    )
    IconButton(
        onClick = {
            imagePicker.launch("image/*")
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.achivo),
            contentDescription ="",
            modifier = Modifier
                .padding(all = dimensionResource(R.dimen.padding_small))
                .fillMaxSize(),
            tint = Color.Unspecified
        )
    }
}

@Composable
private fun ImageCapture(
    viewModel: NoteEntryViewModel,
    modifier:Modifier
){
    val context = LocalContext.current
    var uri=ComposeFileProvider.getImageUri(context)
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if(success){
                viewModel.updatehasImage(success)
                viewModel.updateUrisList(uri)
            }
        }
    )
    val botonCamara = LocalContext.current.applicationContext
    Button(
        onClick = {
            uri = ComposeFileProvider.getImageUri(context)
            cameraLauncher.launch(uri)
            Toast.makeText(botonCamara, R.string.foto, Toast.LENGTH_SHORT).show()
        }
    ) {
        Box{
            Image(
                painter = painterResource(id = R.drawable.camara),
                contentDescription = null,
                modifier = modifier
                    .size(
                        width = dimensionResource(R.dimen.pequeño),
                        height = dimensionResource(R.dimen.pequeño)
                    )
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun verVideos(
    viewModel: NoteEntryViewModel,
    modifier:Modifier=Modifier
) {
    Row(
        modifier= modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Column {
            Column(
                modifier=modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(
                    id = R.string.cantidadVideos)+viewModel.cantidadVideos)
            }
            if (viewModel.hasVideo){
                LazyColumn(modifier = modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(top = 4.dp)) {
                    items(viewModel.urisVideolist.toList()) { uri ->
                        Surface(
                            onClick = {
                                viewModel.updateMostrarVideo(true)
                                viewModel.updateUriMostrar(uri)
                            },
                            modifier = modifier
                                .size(
                                    width = dimensionResource(R.dimen.masGrande),
                                    height = dimensionResource(R.dimen.masGrande)
                                )
                        ){
                            Box{
                                Image(
                                    painter = painterResource(id = R.drawable.video),
                                    contentDescription = null,
                                    modifier = modifier
                                        .size(
                                            width = dimensionResource(R.dimen.masGrande),
                                            height = dimensionResource(R.dimen.masGrande)
                                        )
                                        .aspectRatio(1f),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
                mostrarVideo(viewModel = viewModel, uri = viewModel.uriMostrar)
            }
            Column (
                modifier=modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    modifier=modifier.height(dimensionResource(id = R.dimen.mediano))
                ) {
                    Column(
                        modifier=modifier.weight(0.2f)
                    ) {
                        filesVideoCapture(viewModel = viewModel)
                    }
                    Column(
                        modifier=modifier.weight(0.8f)
                    ) {
                        Button(
                            enabled = viewModel.cantidadVideos!=0,
                            onClick = {
                                viewModel.deleteLastVideoUri()
                            },
                            contentPadding = PaddingValues(10.dp)) {
                            Text(text = stringResource(id = R.string.mensajeEliminarVideo))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun VideoPlayer(
    videoUri: Uri, context:Context
){
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUri))
            prepare()
        }
    }
    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release() // Liberar el exoPlayer
        }
    }
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
            }
        },
        modifier = Modifier.fillMaxWidth(.8f)
    )
}

@Composable
private fun mostrarVideo(
    viewModel: NoteEntryViewModel,
    uri: Uri
){
    if(viewModel.mostrarVideo){
        Dialog(
            properties = DialogProperties(dismissOnClickOutside = true),
            onDismissRequest = { viewModel.updateMostrarVideo(false) }
        ) {
            var Context = LocalContext.current
            VideoPlayer(videoUri = uri, context = Context)
        }
    }
}

@Composable
private fun filesVideoCapture(
    viewModel: NoteEntryViewModel
){
    val videoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            viewModel.updatehasVideo(uri != null)
            if(viewModel.hasVideo){
                viewModel.updateUrisVideoList(uri)
            }
        }
    )
    IconButton(
        onClick = {
            videoPicker.launch("video/*")
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.achivo),
            contentDescription ="",
            modifier = Modifier
                .padding(all = dimensionResource(R.dimen.padding_small))
                .fillMaxSize(),
            tint = Color.Unspecified
        )
    }
}

@Composable
private fun VideoCapture(
    viewModel: NoteEntryViewModel,
    modifier:Modifier
){
    val context = LocalContext.current
    var uri=ComposeFileProvider.getImageUri(context)
    val videoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo(),
        onResult = { success ->
            if(success){
                viewModel.updatehasVideo(success)
                viewModel.updateUrisVideoList(uri)
            }
        }
    )
    val botonCamara = LocalContext.current.applicationContext
    Button(
        onClick = {
            uri = ComposeFileProvider.getVideoUri(context)
            videoLauncher.launch(uri)
            Toast.makeText(botonCamara, R.string.video, Toast.LENGTH_SHORT).show()
        }
    ) {
        Box{
            Image(
                painter = painterResource(id = R.drawable.grabarvideo),
                contentDescription = null,
                modifier = modifier
                    .size(
                        width = dimensionResource(R.dimen.pequeño),
                        height = dimensionResource(R.dimen.pequeño)
                    )
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun verAudios(
    viewModel: NoteEntryViewModel,
    modifier:Modifier=Modifier
) {
    Row(
        modifier= modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Column {
            Row(
                modifier=modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.cantidadAudios)+viewModel.cantidadAudios,
                    modifier=modifier.weight(.4f))
                Row (
                    modifier=modifier.weight(.6f),
                    horizontalArrangement = Arrangement.End
                ){
                    Button(
                        enabled = viewModel.cantidadAudios!=0,
                        onClick = {
                            viewModel.deleteLastUriAudios()
                        },
                        contentPadding = PaddingValues(10.dp)) {
                        Text(text = stringResource(id = R.string.mensajeEliminarAudio))
                    }
                }
            }
            if (viewModel.hasAudio){
                LazyRow(modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)) {
                    items(viewModel.urisAudioslist.toList()) { uri ->
                        Surface(
                            onClick = {
                                viewModel.updateMostrarAudio(true)
                                viewModel.updateUriMostrar(uri)
                            },
                            modifier = modifier
                                .size(width = 64.dp, height = 64.dp)
                        ){
                            Icon(painter = painterResource(id = R.drawable.microfono), contentDescription = "")
                        }
                        Log.d("audiosPrueba",""+uri)
                    }
                }
                Reproducir(viewModel = viewModel, uri = viewModel.uriMostrar)
            }
        }
    }
}

@Composable
private fun Reproducir(
    viewModel: NoteEntryViewModel,
    uri: Uri
){
    val audioPlayer = AndroidAudioPlayer(LocalContext.current)
    if(viewModel.mostrarAudio){
        AlertDialog(
            onDismissRequest = {
                viewModel.updateMostrarAudio(false)
                audioPlayer.stop()
            },
            confirmButton = {
                TextButton(onClick = {
                    audioPlayer.playFile(uri)
                }) {
                    Text(text = stringResource(id = R.string.reproducir))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    audioPlayer.stop()
                    viewModel.updateMostrarAudio(false)
                }) {
                    Text(text = stringResource(id = R.string.cancelar))
                }
            },
            title = { Text(stringResource(id = R.string.audio)) }
        )

    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun AudioCapture(
    viewModel: NoteEntryViewModel,
    modifier:Modifier
){
    val botonMicrofono = LocalContext.current.applicationContext
    Button(
        onClick = {
            viewModel.updateShowAudio(true)
            Toast.makeText(botonMicrofono, R.string.audio, Toast.LENGTH_SHORT).show()
        }
    ) {
        Box{
            Image(
                painter = painterResource(id = R.drawable.microfono),
                contentDescription = null,
                modifier = modifier
                    .size(
                        width = dimensionResource(R.dimen.pequeño),
                        height = dimensionResource(R.dimen.pequeño)
                    )
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
        }
    }
}