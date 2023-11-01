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
import com.davidlopez.proyectofinal_jsdfx.model.Content
import com.davidlopez.proyectofinal_jsdfx.navigation.AppScreens
import com.davidlopez.proyectofinal_jsdfx.viewModel.AppViewModelProvider
import com.davidlopez.proyectofinal_jsdfx.viewModel.NoteDetails
import com.davidlopez.proyectofinal_jsdfx.viewModel.NoteEntryViewModel
import com.davidlopez.proyectofinal_jsdfx.viewModel.NoteUiState

@Composable
fun DespliegueAgregarNotaTarea(
    contentPadding: PaddingValues,
    viewModel: NoteEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    LazyColumn(
        contentPadding=contentPadding,
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))){
        items(DataSourceNotasTareas.texto){
            NoteEntryBody(
                noteUiState = viewModel.noteUiState,
                onNoteValueChange = viewModel::updateUiState)
        }
    }
}

@Composable
fun NoteEntryBody(
    noteUiState: NoteUiState,
    onNoteValueChange: (NoteDetails) -> Unit
){
    AddTextCard(
        noteDetails = noteUiState.noteDetails,
        onValueChange = onNoteValueChange
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTextCard(
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
fun AppAgregarNotaTarea(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: NoteEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
)
{
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
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
                            navController.navigate(route = AppScreens.MainScreen.route)
                            Toast.makeText(botonListo, "Nota agregada", Toast.LENGTH_SHORT).show() },
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
                        .height(dimensionResource(id = R.dimen.masGrande))
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
        },
        bottomBar = {
            Column {
                Row (
                    modifier = Modifier
                        .height(58.dp)
                        .background(Color(0, 46, 195, 255))
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){

                }
                Row (
                    modifier = Modifier
                        .height(78.dp)
                        .background(Color(0, 66, 255, 255))
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    val botonCamara = LocalContext.current.applicationContext
                    Button(
                        onClick = { Toast.makeText(botonCamara, "Camara", Toast.LENGTH_SHORT).show() }
                    ) {
                        Box{
                            Image(
                                painter = painterResource(id = R.drawable.camara),
                                contentDescription = null,
                                modifier = modifier
                                    .size(
                                        width = dimensionResource(R.dimen.mediano),
                                        height = dimensionResource(R.dimen.mediano)
                                    )
                                    .aspectRatio(1f),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_smaller)))
                    val botonGaleria = LocalContext.current.applicationContext
                    Button(
                        onClick = { Toast.makeText(botonGaleria, "Galeria", Toast.LENGTH_SHORT).show() }
                    ) {
                        Box{
                            Image(
                                painter = painterResource(id = R.drawable.image),
                                contentDescription = null,
                                modifier = modifier
                                    .size(
                                        width = dimensionResource(R.dimen.mediano),
                                        height = dimensionResource(R.dimen.mediano)
                                    )
                                    .aspectRatio(1f),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_smaller)))
                    val botonArchivo = LocalContext.current.applicationContext
                    Button(
                        onClick = { Toast.makeText(botonArchivo, "Archivo", Toast.LENGTH_SHORT).show() }
                    ) {
                        Box{
                            Image(
                                painter = painterResource(id = R.drawable.achivo),
                                contentDescription = null,
                                modifier = modifier
                                    .size(
                                        width = dimensionResource(R.dimen.mediano),
                                        height = dimensionResource(R.dimen.mediano)
                                    )
                                    .aspectRatio(1f),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_smaller)))
                    val botonMicrofono = LocalContext.current.applicationContext
                    Button(
                        onClick = { Toast.makeText(botonMicrofono, "Micrófono", Toast.LENGTH_SHORT).show() }
                    ) {
                        Box{
                            Image(
                                painter = painterResource(id = R.drawable.microfono),
                                contentDescription = null,
                                modifier = modifier
                                    .size(
                                        width = dimensionResource(R.dimen.mediano),
                                        height = dimensionResource(R.dimen.mediano)
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
        DespliegueAgregarNotaTarea(contentPadding = it)
    }
}

@Composable
fun TituloNoteEntryBody(
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
fun TituloNota(
    noteDetails: NoteDetails,
    onValueChange: (NoteDetails) -> Unit = {}
){
    TextField(
        value = noteDetails.titulo,
        onValueChange = {onValueChange(noteDetails.copy(titulo = it))},
        modifier = Modifier
            .padding(start = 8.dp, top = 4.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        label = { Text(text = stringResource(id = R.string.titulo)) },
        textStyle = MaterialTheme.typography.bodyLarge
    )
}