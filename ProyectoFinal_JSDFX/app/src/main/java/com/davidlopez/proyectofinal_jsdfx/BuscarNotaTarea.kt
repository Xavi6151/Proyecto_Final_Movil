package com.davidlopez.proyectofinal_jsdfx

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.davidlopez.proyectofinal_jsdfx.data.DataSourceNotasTareas
import com.davidlopez.proyectofinal_jsdfx.model.Notas
import com.davidlopez.proyectofinal_jsdfx.model.Tareas

@Composable
fun DespliegueBuscar(modifier: Modifier = Modifier, contentPadding: PaddingValues = PaddingValues(0.dp)){
    LazyColumn(
        contentPadding = contentPadding
    ){
        items(DataSourceNotasTareas.listaNotas){ notas ->
            MenuNotasBuscar(notas)
        }
        items(DataSourceNotasTareas.listaTareas){ tareas ->
            MenuTareasBuscar(tareas)
        }
    }
}

@Composable
fun MenuNotasBuscar(notas: Notas, modifier: Modifier = Modifier){
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
fun MenuTareasBuscar(tareas: Tareas, modifier: Modifier = Modifier){
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
fun AppBuscar(
    modifier: Modifier = Modifier,
    navController: NavController
){
    //Definir barra superior e inferior
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column {
                Row (
                    modifier = Modifier
                        .height(86.dp)
                        .background(Color(0, 66, 255, 255))
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
                            ),
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
                }
            }
        },
        bottomBar = {
            Column {
                Row (
                    modifier = Modifier
                        .height(64.dp)
                        .background(Color(0, 66, 255, 255))
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }
    ){
        DespliegueBuscar(contentPadding = it)
    }
}