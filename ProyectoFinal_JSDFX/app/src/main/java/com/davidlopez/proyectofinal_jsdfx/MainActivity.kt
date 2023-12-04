package com.davidlopez.proyectofinal_jsdfx

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.Update
import com.davidlopez.proyectofinal_jsdfx.data.DataSourceNotasTareas.listaNotas
import com.davidlopez.proyectofinal_jsdfx.data.DataSourceNotasTareas.listaTareas
import com.davidlopez.proyectofinal_jsdfx.data.NotaEntity
import com.davidlopez.proyectofinal_jsdfx.model.Notas
import com.davidlopez.proyectofinal_jsdfx.navigation.AppNavigation
import com.davidlopez.proyectofinal_jsdfx.navigation.AppScreens
import com.davidlopez.proyectofinal_jsdfx.playback.AndroidAudioPlayer
import com.davidlopez.proyectofinal_jsdfx.record.AndroidAudioRecorder
import com.davidlopez.proyectofinal_jsdfx.sizeScreen.WindowInfo
import com.davidlopez.proyectofinal_jsdfx.sizeScreen.rememberWindowInfo
import com.davidlopez.proyectofinal_jsdfx.ui.theme.ProyectoFinal_JSDFXTheme
import com.davidlopez.proyectofinal_jsdfx.viewModel.AppViewModelProvider
import com.davidlopez.proyectofinal_jsdfx.viewModel.HomeViewModel
import com.davidlopez.proyectofinal_jsdfx.viewModel.NoteEntryViewModel
import kotlinx.coroutines.launch
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.POST_NOTIFICATIONS),
            0)
        setContent {
            ProyectoFinal_JSDFXTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                    //val audioViewModel: AudioViewModel = viewModel()
                    //ejemplo(audioViewModel, cacheDir)
                    obtenerCacheDir(cacheDir)
                }
            }
        }
    }
}

@Composable
fun Despliegue(
    listaNotas: List<NotaEntity>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onNoteClick: (NotaEntity) -> Unit,
    viewModelHome: HomeViewModel
){
    val tamanioPantalla = rememberWindowInfo()
    val coroutineScope = rememberCoroutineScope()
    val message= LocalContext.current.applicationContext
    VentanaDialogo(
        show = viewModelHome.show ,
        onDismiss = { viewModelHome.updateShow(false) },
        onConfirm = {
            coroutineScope.launch {
                viewModelHome.deleteNote(notaEliminar)
                Toast.makeText(message,"Eliminada exitosamente", Toast.LENGTH_SHORT).show()
            }
            viewModelHome.updateShow(false)
        },
        titulo = stringResource(id = R.string.eliminarNota),
        text = stringResource(id = R.string.preguntaEliminar)
    )
    if(tamanioPantalla.screenWindthInfo is WindowInfo.WindowType.Compact){
        LazyColumn(
            contentPadding = contentPadding
        ){
            items(items = listaNotas, key = {it.id}){nota->
                MenuNotas(notas = nota,
                    modifierEdit = Modifier.clickable { onNoteClick(nota) },
                    viewModelHome,
                    nota
                )
            }
        }
    }else{
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding=contentPadding,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ){
            items(items = listaNotas, key = {it.id}){nota->
                MenuNotas(notas = nota,
                    modifierEdit = Modifier.clickable { onNoteClick(nota) },
                    viewModelHome,
                    nota
                )
            }
        }
    }
}

var notaEliminar:NotaEntity = NotaEntity(0,"","","")

@Composable
private fun MenuNotas(
    notas: NotaEntity,
    modifierEdit: Modifier = Modifier,
    viewModelHome: HomeViewModel,
    nota: NotaEntity,
    modifier: Modifier = Modifier
    ){
    Card(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_smaller))
    ){
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Box(modifier = modifierEdit.align(CenterVertically)){
                Icon(
                    painter = painterResource(id = R.drawable.nota),
                    contentDescription = null,
                    modifier = modifier
                        .size(
                            width = dimensionResource(R.dimen.grande),
                            height = dimensionResource(R.dimen.grande)
                        )
                        .aspectRatio(1f)
                        .padding(dimensionResource(R.dimen.padding_small)),
                    tint = Color.Unspecified
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = notas.titulo,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(
                                start = dimensionResource(R.dimen.padding_small),
                                top = dimensionResource(R.dimen.padding_smaller)
                            )
                            .weight(1f)
                    )
                    Text(
                        text = notas.fecha,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(
                            start = dimensionResource(R.dimen.padding_small),
                            top = dimensionResource(R.dimen.padding_small),
                            end = dimensionResource(R.dimen.padding_small)
                        )
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = notas.contenido,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(
                            start = dimensionResource(R.dimen.padding_small),
                            top = dimensionResource(R.dimen.padding_small),
                            end = dimensionResource(R.dimen.padding_larger)
                        )
                    )
                }
            }
            Box(modifier = modifierEdit.align(CenterVertically)){
                Button(
                    onClick = {
                        notaEliminar=nota
                        viewModelHome.updateShow(true)
                    },
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.basura),
                        contentDescription = null,
                        modifier = modifier
                            .size(
                                width = dimensionResource(R.dimen.grande),
                                height = dimensionResource(R.dimen.grande)
                            )
                            .aspectRatio(1f)
                            .padding(dimensionResource(R.dimen.padding_small)),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: NoteEntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    viewModelHome: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateToItemUpdate: (NotaEntity) -> Unit
){
    val homeUiState by viewModelHome.homeUiState.collectAsState()
    val tamanioPantalla = rememberWindowInfo()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if(tamanioPantalla.screenWindthInfo is WindowInfo.WindowType.Compact){
                parteDeArribaCompacta(modifier = Modifier, viewModel = viewModel, navController = navController)
            }else{
                parteDeArribaExtendida(modifier = Modifier, viewModel = viewModel, navController = navController)
            }
        }
    ) {
        HomeBody(notaList = homeUiState.noteList, contentPadding = it, onNoteClick = navigateToItemUpdate, viewModelHome = viewModelHome)
    }
}

@Composable
private fun HomeBody(
    notaList: List<NotaEntity>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    onNoteClick: (NotaEntity) -> Unit,
    viewModelHome: HomeViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (notaList.isEmpty()) {
            LazyColumn(
                contentPadding=contentPadding,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ){
                item{
                    Text(
                        text = stringResource(R.string.sinNota),
                        textAlign = TextAlign.Center,
                        modifier= modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        } else {
            Despliegue(
                listaNotas = notaList,
                contentPadding = contentPadding,
                onNoteClick = { onNoteClick(it) },
                viewModelHome = viewModelHome
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun parteDeArribaCompacta(
    modifier: Modifier = Modifier,
    viewModel: NoteEntryViewModel,
    navController: NavController
){
    Column {
        Row (
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.mediano))
                .background(Color(0, 66, 255, 255))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineLarge
            )
        }
        Row(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.grande))
                .background(Color(0, 128, 255, 255))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            TextField(
                value = "Search",
                textStyle = MaterialTheme.typography.bodyLarge,
                onValueChange = {},
                modifier = modifier
                    .height(52.dp)
                    .weight(1f)
                    .padding(
                        start = dimensionResource(R.dimen.padding_small),
                        end = dimensionResource(R.dimen.padding_small)
                    ).fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.buscar),
                        contentDescription = null,
                        modifier = modifier
                            .padding(
                                start = dimensionResource(R.dimen.padding_small),
                                top = dimensionResource(R.dimen.padding_small),
                                end = dimensionResource(R.dimen.padding_small),
                                bottom = dimensionResource(R.dimen.padding_small)
                            )
                    )
                }
            )
            val botonAgregar = LocalContext.current.applicationContext
            IconButton(
                onClick = {
                    navController.navigate(route = AppScreens.AddScreen.route)
                    Toast.makeText(botonAgregar, R.string.agregar, Toast.LENGTH_SHORT).show() },
                modifier = Modifier.size(
                    width = dimensionResource(R.dimen.medianoGrande),
                    height = dimensionResource(R.dimen.medianoGrande)
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.a_adir),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = dimensionResource(R.dimen.padding_small))
                        .fillMaxSize(), // La imagen ocupará todo el espacio del botón
                    tint = Color.Unspecified // Puedes ajustar el color de la imagen si lo deseas
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun parteDeArribaExtendida(
    modifier: Modifier = Modifier,
    viewModel: NoteEntryViewModel,
    navController: NavController
){
    Column {
        Row (
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.grande))
                .background(Color(0, 66, 255, 255))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                modifier = modifier
                    .padding(start = dimensionResource(R.dimen.padding_large))
            )
            Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
            val botonAgregar = LocalContext.current.applicationContext
            IconButton(
                onClick = {
                    navController.navigate(route = AppScreens.AddScreen.route)
                    Toast.makeText(botonAgregar, R.string.agregar, Toast.LENGTH_SHORT).show() },
                modifier = Modifier.size(
                    width = dimensionResource(R.dimen.medianoGrande),
                    height = dimensionResource(R.dimen.medianoGrande)
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.a_adir),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = dimensionResource(R.dimen.padding_small))
                        .fillMaxSize(), // La imagen ocupará todo el espacio del botón
                    tint = Color.Unspecified // Puedes ajustar el color de la imagen si lo deseas
                )
            }
        }
    }
}

@Composable
fun VentanaDialogo(
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
                TextButton(onClick = {onConfirm() }) {
                    Text(text = stringResource(id = R.string.confirmar))
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(text = stringResource(id = R.string.cancelar))
                }
            },
            title = { Text(titulo) },
            text = { Text(text) }
        )
    }
}
