package com.andoapps.thundertest.ui.screens

import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FilesScreen(paddingValues: PaddingValues) {
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }
    var urlInput by remember { mutableStateOf(TextFieldValue("")) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            Log.d("FilesScreen", "Archivo seleccionado: $it")
            // TODO: Procesar archivo local
        }
    }

    val legacyPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            Log.d("FilesScreen", "Archivo seleccionado (legacy): $it")
            // TODO: Procesar archivo local
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues), // El padding del Scaffold ya incluye el espacio del TopAppBar
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // El CenterAlignedTopAppBar ha sido eliminado de aquí

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                        )
                    } else {
                        legacyPickerLauncher.launch(arrayOf("image/*", "video/*"))
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Añadir localmente")
            }

            Button(
                onClick = {
                    showDialog = true
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Añadir desde Red")
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Text(
                    text = "Añade archivos desde un origen local o de red.",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Flujo de la red") },
            text = {
                Column {
                    Text("Introduce una URL de la red:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = urlInput,
                        onValueChange = { urlInput = it },
                        singleLine = true,
                        placeholder = { Text("http://example.com/video.mp4") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        val url = urlInput.text
                        if (url.isNotBlank()) {
                            Log.d("FilesScreen", "URL ingresada: $url")
                            // TODO: Procesar reproducción o carga desde red
                        }
                    }
                ) {
                    Text("Hecho")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}