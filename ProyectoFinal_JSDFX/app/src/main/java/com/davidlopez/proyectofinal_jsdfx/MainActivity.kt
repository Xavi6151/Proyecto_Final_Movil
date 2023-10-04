package com.davidlopez.proyectofinal_jsdfx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidlopez.proyectofinal_jsdfx.data.DataSourceNotasTareas
import com.davidlopez.proyectofinal_jsdfx.model.Notas
import com.davidlopez.proyectofinal_jsdfx.model.Tareas
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
                    app()
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
                    painter = painterResource(id = R.drawable.image),
                    contentDescription = null,
                    modifier = modifier
                        .size(
                            width = dimensionResource(R.dimen.width),
                            height = dimensionResource(R.dimen.height)
                        )
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = stringResource(id = notas.nombre),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_small),
                        top = dimensionResource(R.dimen.padding_smaller)
                    )
                )
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
            Column {
                Text(
                    text = stringResource(id = notas.fecha),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_larger),
                        end = dimensionResource(R.dimen.padding_medium)
                    )
                )
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
                    painter = painterResource(id = R.drawable.image),
                    contentDescription = null,
                    modifier = modifier
                        .size(
                            width = dimensionResource(R.dimen.width),
                            height = dimensionResource(R.dimen.height)
                        )
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = stringResource(id = tareas.nombre),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_small),
                        top = dimensionResource(R.dimen.padding_smaller)
                    )
                )
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
            Column {
                Text(
                    text = stringResource(id = tareas.fechaInicio),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_larger),
                        end = dimensionResource(R.dimen.padding_small)
                    )
                )
            }
            Column {
                Text(
                    text = stringResource(id = tareas.fechaFinal),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_smaller),
                        top = dimensionResource(R.dimen.padding_larger),
                        end = dimensionResource(R.dimen.padding_medium)
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun app(modifier: Modifier = Modifier){
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
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .background(Color(0, 128, 255, 255))
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = stringResource(id = R.string.ordenado),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .height(128.dp)
                    .background(Color(0, 66, 255, 255))
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Button(
                    onClick = { /*TODO*/ }
                ) {
                    Box{
                        Image(
                            painter = painterResource(id = R.drawable.book),
                            contentDescription = null,
                            modifier = modifier
                                .size(
                                    width = dimensionResource(R.dimen.width),
                                    height = dimensionResource(R.dimen.height)
                                )
                                .aspectRatio(1f),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Button(onClick = { /*TODO*/ }) {
                    Box{
                        Image(
                            painter = painterResource(id = R.drawable.image),
                            contentDescription = null,
                            modifier = modifier
                                .size(
                                    width = dimensionResource(R.dimen.width),
                                    height = dimensionResource(R.dimen.height)
                                )
                                .aspectRatio(1f),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Button(onClick = { /*TODO*/ }) {
                    Box{
                        Image(
                            painter = painterResource(id = R.drawable.lupa),
                            contentDescription = null,
                            modifier = modifier
                                .size(
                                    width = dimensionResource(R.dimen.width),
                                    height = dimensionResource(R.dimen.height)
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