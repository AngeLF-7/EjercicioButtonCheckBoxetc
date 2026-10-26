package com.example.ejerciciobuttoncheckboxetc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PantallaBasica()
                }
            }
        }
    }
}

@Composable
fun PantallaBasica() {
    val coroutineScope = rememberCoroutineScope()

    var textoBoton by remember { mutableStateOf("Presionar") }
    var mensajeTexto by remember { mutableStateOf("Mensaje oculto") }
    var mostrarTexto by remember { mutableStateOf(false) }
    var cargando by remember { mutableStateOf(false) }
    var switchActivo by remember { mutableStateOf(false) }
    var opcionSeleccionada by remember { mutableStateOf("Android Studio") }

    // LISTA DE IMÁGENES
    val imagenes = listOf(
        R.drawable.imagen1,
        R.drawable.imagen2,
        R.drawable.imagen3
    )
    var indiceImagen by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ICONO PERSONALIZADO
        Icon(
            painter = painterResource(id = R.drawable.icono),
            contentDescription = "Icono personalizado",
            tint = Color.Unspecified,
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // BOTÓN
        Button(
            onClick = {
                textoBoton = "Botón presionado"
                indiceImagen = (indiceImagen + 1) % imagenes.size
                cargando = true

                coroutineScope.launch {
                    delay(5000)
                    cargando = false
                }
            }
        ) {
            Text(textoBoton)
        }

        if (cargando) {
            Spacer(modifier = Modifier.height(8.dp))
            CircularProgressIndicator()
        }

        Spacer(modifier = Modifier.height(16.dp))

        // CHECKBOX
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = mostrarTexto,
                onCheckedChange = { mostrarTexto = it }
            )
            Text("Activar")
        }

        // CAMPO DE TEXTO OCULTO
        if (mostrarTexto) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = mensajeTexto)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // SWITCH
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Mostrar opciones: ")
            Switch(
                checked = switchActivo,
                onCheckedChange = { switchActivo = it }
            )
        }

        // RADIO BUTTONS
        if (switchActivo) {
            Spacer(modifier = Modifier.height(8.dp))
            Column {
                val opciones = listOf("Android Studio", "Kotlin", "Java")

                for (opcion in opciones) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = (opcionSeleccionada == opcion),
                            onClick = {
                                opcionSeleccionada = opcion
                                mensajeTexto = opcion
                            }
                        )
                        Text(text = opcion)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // IMAGEN QUE CAMBIA AL HACER CLIC EN EL BOTÓN
        Image(
            painter = painterResource(id = imagenes[indiceImagen]),
            contentDescription = "Imagen cambiante",
            modifier = Modifier.size(150.dp)
        )
    }
}