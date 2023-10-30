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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.navigation.NavController
import com.davidlopez.proyectofinal_jsdfx.data.DataSourceNotasTareas
import com.davidlopez.proyectofinal_jsdfx.model.Notas
import com.davidlopez.proyectofinal_jsdfx.model.Tareas
import com.davidlopez.proyectofinal_jsdfx.navigation.AppNavigation
import com.davidlopez.proyectofinal_jsdfx.navigation.AppScreens
import com.davidlopez.proyectofinal_jsdfx.ui.theme.ProyectoFinal_JSDFXTheme

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
    LazyColumn(
        contentPadding = contentPadding
    ){
        items(DataSourceNotasTareas.listaNotas){ notas ->
            MenuNotas(notas)
        }
        items(DataSourceNotasTareas.listaTareas){ tareas ->
            MenuTareas(tareas)
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
                        .padding(
                            start = dimensionResource(R.dimen.padding_small),
                            top = dimensionResource(R.dimen.padding_small),
                            bottom = dimensionResource(R.dimen.padding_small),
                            end = dimensionResource(R.dimen.padding_small)
                        ),
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
                        modifier = Modifier.padding(
                            start = dimensionResource(R.dimen.padding_small),
                            top = dimensionResource(R.dimen.padding_smaller)
                        ).weight(1f)
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

@Composable
fun MenuTareas(tareas: Tareas, modifier: Modifier = Modifier){
    Card(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_smaller))
    ){
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Box{
                Image(
                    painter = painterResource(id = R.drawable.tarea),
                    contentDescription = null,
                    modifier = modifier
                        .size(
                            width = dimensionResource(R.dimen.grande),
                            height = dimensionResource(R.dimen.grande)
                        )
                        .aspectRatio(1f)
                        .padding(
                            start = dimensionResource(R.dimen.padding_small),
                            top = dimensionResource(R.dimen.padding_small),
                            bottom = dimensionResource(R.dimen.padding_small),
                            end = dimensionResource(R.dimen.padding_small)
                        ),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = stringResource(id = tareas.nombre),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(
                            start = dimensionResource(R.dimen.padding_small),
                            top = dimensionResource(R.dimen.padding_smaller)
                        ).weight(1f)
                    )
                    Text(
                        text = stringResource(id = tareas.fechaInicio),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(
                            start = dimensionResource(R.dimen.padding_small),
                            top = dimensionResource(R.dimen.padding_small)
                        )
                    )
                    Text(
                        text = stringResource(id = tareas.fechaFinal),
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
                        text = stringResource(id = tareas.descripcion),
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
    navController: NavController
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column {
                Row (
                    modifier = Modifier
                        .height(64.dp)
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
                        .height(46.dp)
                        .background(Color(0, 128, 255, 255))
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = stringResource(id = R.string.ordenado),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
                    ExposedDropdownMenuBox(
                        modifier = modifier
                            .height(36.dp)
                            .width(150.dp)
                            .padding(0.dp),
                        expanded = false,
                        onExpandedChange = {}
                    ) {
                        TextField(
                            value = stringResource(id = R.string.nombre),
                            onValueChange = {},
                            modifier = modifier.padding(0.dp).menuAnchor(),
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) }
                        )
                    }
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .height(108.dp)
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
                                    width = dimensionResource(R.dimen.grande),
                                    height = dimensionResource(R.dimen.grande)
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
                        width = dimensionResource(R.dimen.masGrande),
                        height = dimensionResource(R.dimen.masGrande)
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
                                    width = dimensionResource(R.dimen.grande),
                                    height = dimensionResource(R.dimen.grande)
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
