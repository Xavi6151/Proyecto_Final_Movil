package com.davidlopez.proyectofinal_jsdfx

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
import androidx.compose.material3.Button
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
import androidx.navigation.NavController
import com.davidlopez.proyectofinal_jsdfx.data.DataSourceNotasTareas
import com.davidlopez.proyectofinal_jsdfx.model.Content
import com.davidlopez.proyectofinal_jsdfx.navigation.AppScreens

@Composable
fun DespliegueAgregarNotaTarea(contentPadding: PaddingValues = PaddingValues(0.dp)){
    LazyColumn(
        contentPadding=contentPadding,
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))){
        items(DataSourceNotasTareas.texto){
            AddTextCard(content = it)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTextCard(content: Content){
    TextField(
        value = stringResource(content.text),
        onValueChange = {},
        modifier = Modifier
            .padding(top = 4.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent),
        textStyle = MaterialTheme.typography.bodyMedium)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppAgregarNotaTarea(
    modifier: Modifier = Modifier,
    navController: NavController
)
{
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column {
                Row (
                    modifier = Modifier
                        .height(64.dp)
                        .background(Color(0, 66, 255, 255))
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    TextField(
                        value = stringResource(id = R.string.titulo),
                        onValueChange = {},
                        modifier = Modifier
                            .padding(start = 8.dp, top = 4.dp)
                            .height(48.dp)
                            .weight(1f),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
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
                        .height(64.dp)
                        .background(Color(0, 46, 195, 255))
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    ExposedDropdownMenuBox(
                        modifier = modifier
                            .height(48.dp)
                            .padding(start = 8.dp, end = 8.dp),
                        expanded = false,
                        onExpandedChange = {}
                    ) {
                        TextField(
                            value = stringResource(id = R.string.titulo_nota),
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                            modifier = Modifier
                                .fillMaxSize()
                        )
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