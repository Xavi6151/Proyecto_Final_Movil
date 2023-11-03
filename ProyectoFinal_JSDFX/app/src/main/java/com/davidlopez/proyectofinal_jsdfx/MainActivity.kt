package com.davidlopez.proyectofinal_jsdfx

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.TextField
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
import com.davidlopez.proyectofinal_jsdfx.data.DataSourceNotasTareas.listaNotas
import com.davidlopez.proyectofinal_jsdfx.data.DataSourceNotasTareas.listaTareas
import com.davidlopez.proyectofinal_jsdfx.model.Notas
import com.davidlopez.proyectofinal_jsdfx.navigation.AppNavigation
import com.davidlopez.proyectofinal_jsdfx.navigation.AppScreens
import com.davidlopez.proyectofinal_jsdfx.sizeScreen.WindowInfo
import com.davidlopez.proyectofinal_jsdfx.sizeScreen.rememberWindowInfo
import com.davidlopez.proyectofinal_jsdfx.ui.theme.ProyectoFinal_JSDFXTheme
import com.davidlopez.proyectofinal_jsdfx.viewModel.AppViewModelProvider
import com.davidlopez.proyectofinal_jsdfx.viewModel.NoteEntryViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoFinal_JSDFXTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun Despliegue(modifier: Modifier = Modifier, contentPadding: PaddingValues = PaddingValues(0.dp)){
    val tamanioPantalla = rememberWindowInfo()
    if(tamanioPantalla.screenWindthInfo is WindowInfo.WindowType.Compact){
        LazyColumn(
            contentPadding = contentPadding
        ){
            items(listaNotas){ notas ->
                MenuNotas(notas)
            }
            items(listaTareas){ tareas ->
                MenuNotas(tareas)
            }
        }
    }else if(tamanioPantalla.screenWindthInfo is WindowInfo.WindowType.Medium){
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding=contentPadding,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ){
            itemsIndexed(listaNotas){ id,notas ->
                MenuNotas(notas)
            }
            itemsIndexed(listaTareas){ id,tareas ->
                MenuNotas(tareas)
            }
        }
    }else{
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding=contentPadding,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ){
            itemsIndexed(listaNotas){ id,notas ->
                MenuNotas(notas)
            }
            itemsIndexed(listaTareas){ id,tareas ->
                MenuNotas(tareas)
            }
        }
    }
}

@Composable
fun MenuNotas(notas: Notas, modifier: Modifier = Modifier){
    Card(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_smaller))
    ){
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Box{
                Image(
                    painter = painterResource(id = R.drawable.nota),
                    contentDescription = null,
                    modifier = modifier
                        .size(
                            width = dimensionResource(R.dimen.grande),
                            height = dimensionResource(R.dimen.grande)
                        )
                        .aspectRatio(1f)
                        .padding(dimensionResource(R.dimen.padding_small)),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = stringResource(id = notas.nombre),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(
                                start = dimensionResource(R.dimen.padding_small),
                                top = dimensionResource(R.dimen.padding_smaller)
                            )
                            .weight(1f)
                    )
                    Text(
                        text = stringResource(id = notas.fecha),
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
                        text = stringResource(id = notas.descripcion),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(
                            start = dimensionResource(R.dimen.padding_small),
                            top = dimensionResource(R.dimen.padding_small),
                            end = dimensionResource(R.dimen.padding_larger)
                        )
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
    viewModel: NoteEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val tamanioPantalla = rememberWindowInfo()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if(tamanioPantalla.screenWindthInfo is WindowInfo.WindowType.Compact){
                parteDeArribaCompacta(modifier = Modifier, viewModel = viewModel)
            }else{
                parteDeArribaExtendida(modifier = Modifier, viewModel = viewModel)
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.masGrande))
                    .background(Color(0, 66, 255, 255))
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                val botonIdioma = LocalContext.current.applicationContext
                Button(
                    onClick = { Toast.makeText(botonIdioma, "Español", Toast.LENGTH_SHORT).show() }
                ) {
                    Box{
                        Image(
                            painter = painterResource(id = R.drawable.traductor),
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
                Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
                val botonAgregar = LocalContext.current.applicationContext
                IconButton(
                    onClick = {
                        navController.navigate(route = AppScreens.AddScreen.route)
                        Toast.makeText(botonAgregar, "Agregar", Toast.LENGTH_SHORT).show() },
                    modifier = Modifier.size(
                        width = dimensionResource(R.dimen.grande),
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
                Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
                val botonBuscar = LocalContext.current.applicationContext
                Button(onClick = {
                    navController.navigate(route = AppScreens.SearchScreen.route)
                    Toast.makeText(botonBuscar, "Buscar", Toast.LENGTH_SHORT).show() }
                ) {
                    Box{
                        Image(
                            painter = painterResource(id = R.drawable.buscar),
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
    ) {
        Despliegue(contentPadding = it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun parteDeArribaCompacta(
    modifier: Modifier = Modifier,
    viewModel: NoteEntryViewModel
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
            ExposedDropdownMenuBox(
                modifier = modifier
                    .padding(start = 8.dp, end = 8.dp),
                expanded = viewModel.expandidoOrden,
                onExpandedChange = {viewModel.actualizarExpandidoOrden(it)}
            ) {
                TextField(
                    value = viewModel.ordenado,
                    onValueChange = {},
                    readOnly = true,
                    label = { androidx.compose.material.Text(stringResource(id = R.string.ordenado)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                    modifier = Modifier.menuAnchor(),
                )
                ExposedDropdownMenu(
                    expanded = viewModel.expandidoOrden,
                    onDismissRequest = { viewModel.expandidoOrden=false })
                {
                    var texto1 = stringResource(id = R.string.nombre)
                    var texto2 = stringResource(id = R.string.creado)
                    DropdownMenuItem(
                        text = { androidx.compose.material.Text(texto1) },
                        onClick = {
                            viewModel.actualizarOrdenado(texto1)
                            viewModel.expandidoOrden=false
                        }
                    )
                    DropdownMenuItem(
                        text = { androidx.compose.material.Text(texto2) },
                        onClick = {
                            viewModel.actualizarOrdenado(texto2)
                            viewModel.expandidoOrden=false
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun parteDeArribaExtendida(
    modifier: Modifier = Modifier,
    viewModel: NoteEntryViewModel
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
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
            ExposedDropdownMenuBox(
                modifier = modifier
                    .padding(start = 8.dp, end = 8.dp),
                expanded = viewModel.expandidoOrden,
                onExpandedChange = {viewModel.actualizarExpandidoOrden(it)}
            ) {
                TextField(
                    value = viewModel.ordenado,
                    onValueChange = {},
                    readOnly = true,
                    label = { androidx.compose.material.Text(stringResource(id = R.string.ordenado)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                    modifier = Modifier.menuAnchor(),
                )
                ExposedDropdownMenu(
                    expanded = viewModel.expandidoOrden,
                    onDismissRequest = { viewModel.expandidoOrden=false })
                {
                    var texto1 = stringResource(id = R.string.nombre)
                    var texto2 = stringResource(id = R.string.creado)
                    DropdownMenuItem(
                        text = { androidx.compose.material.Text(texto1) },
                        onClick = {
                            viewModel.actualizarOrdenado(texto1)
                            viewModel.expandidoOrden=false
                        }
                    )
                    DropdownMenuItem(
                        text = { androidx.compose.material.Text(texto2) },
                        onClick = {
                            viewModel.actualizarOrdenado(texto2)
                            viewModel.expandidoOrden=false
                        }
                    )
                }
            }
        }
    }
}
