package com.davidlopez.proyectofinal_jsdfx

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.davidlopez.proyectofinal_jsdfx.data.DataSourceNotasTareas
import com.davidlopez.proyectofinal_jsdfx.data.NotaEntity
import com.davidlopez.proyectofinal_jsdfx.model.Content
import com.davidlopez.proyectofinal_jsdfx.navigation.AppScreens
import com.davidlopez.proyectofinal_jsdfx.sizeScreen.WindowInfo
import com.davidlopez.proyectofinal_jsdfx.sizeScreen.rememberWindowInfo
import com.davidlopez.proyectofinal_jsdfx.viewModel.AppViewModelProvider
import com.davidlopez.proyectofinal_jsdfx.viewModel.NoteDetails
import com.davidlopez.proyectofinal_jsdfx.viewModel.NoteEditViewModel
import com.davidlopez.proyectofinal_jsdfx.viewModel.NoteEntryViewModel
import com.davidlopez.proyectofinal_jsdfx.viewModel.NoteUiState
import com.davidlopez.proyectofinal_jsdfx.viewModel.toNoteDetails
import kotlinx.coroutines.launch

@Composable
fun DespliegueEditarNotaTarea(
    contentPadding: PaddingValues,
    viewModel: NoteEditViewModel
){
    LazyColumn(
        contentPadding=contentPadding,
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))){
        items(DataSourceNotasTareas.texto){
            NoteEditBody(
                noteUiState = viewModel.noteUiState,
                onNoteValueChange = viewModel::updateUiStateEdit)
        }
    }
}

@Composable
fun NoteEditBody(
    noteUiState: NoteUiState,
    onNoteValueChange: (NoteDetails) -> Unit
){
    EditTextCard(
        noteDetails = noteUiState.noteDetails,
        onValueChange = onNoteValueChange
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextCard(
    noteDetails: NoteDetails,
    onValueChange: (NoteDetails) -> Unit = {}
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppEditarNotaTarea(
    modifier: Modifier = Modifier,
    navController: NavController,
    notaEntity: NotaEntity,
    viewModel: NoteEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
)
{
    viewModel.updateUiStateEdit(notaEntity.toNoteDetails())
    val tamanioPantalla = rememberWindowInfo()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if(tamanioPantalla.screenWindthInfo is WindowInfo.WindowType.Compact){
                parteDeArriba2CompactaEditar(modifier = Modifier, navController = navController, viewModel = viewModel)
            }else{
                parteDeArriba2ExtendidaEditar(modifier = Modifier, navController = navController, viewModel = viewModel)
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
                    val botonCamara = LocalContext.current.applicationContext
                    Button(
                        onClick = { Toast.makeText(botonCamara, R.string.foto, Toast.LENGTH_SHORT).show() }
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
                    Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
                    val botonGaleria = LocalContext.current.applicationContext
                    Button(
                        onClick = { Toast.makeText(botonGaleria, R.string.archivos, Toast.LENGTH_SHORT).show() }
                    ) {
                        Box{
                            Image(
                                painter = painterResource(id = R.drawable.image),
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
                    Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
                    val botonArchivo = LocalContext.current.applicationContext
                    Button(
                        onClick = { Toast.makeText(botonArchivo, R.string.archivos, Toast.LENGTH_SHORT).show() }
                    ) {
                        Box{
                            Image(
                                painter = painterResource(id = R.drawable.achivo),
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
                    Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
                    val botonMicrofono = LocalContext.current.applicationContext
                    Button(
                        onClick = { Toast.makeText(botonMicrofono, R.string.audio, Toast.LENGTH_SHORT).show() }
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
            }
        }
    ){
        DespliegueEditarNotaTarea(contentPadding = it, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun parteDeArriba2CompactaEditar(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: NoteEditViewModel
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
                TituloNoteEntryBody(noteUiState = viewModel.noteUiState, onNoteValueChange = viewModel::updateUiStateEdit)
            }
            Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_small)))
            val botonListo = LocalContext.current.applicationContext
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.updateNote()
                        navController.navigate(route = AppScreens.MainScreen.route)
                        Toast.makeText(botonListo, R.string.editarAccion, Toast.LENGTH_SHORT).show()
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
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(texto2) },
                        onClick = {
                            viewModel.actualizarOpcion(texto2)
                            viewModel.expandidoTipo=false
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
fun parteDeArriba2ExtendidaEditar(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: NoteEditViewModel
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
                TituloNoteEntryBody(noteUiState = viewModel.noteUiState, onNoteValueChange = viewModel::updateUiStateEdit)
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
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(texto2) },
                        onClick = {
                            viewModel.actualizarOpcion(texto2)
                            viewModel.expandidoTipo=false
                        }
                    )
                }
            }
            //Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_smaller)))
            val botonListo = LocalContext.current.applicationContext
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.updateNote()
                        navController.navigate(route = AppScreens.MainScreen.route)
                        Toast.makeText(botonListo, R.string.editarAccion, Toast.LENGTH_SHORT).show()
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