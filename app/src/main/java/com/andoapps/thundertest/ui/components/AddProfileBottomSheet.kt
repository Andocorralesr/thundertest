// ruta: com/andoapps/thundertest/ui/components/AddProfileBottomSheet.kt

package com.andoapps.thundertest.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.andoapps.thundertest.data.model.Profile
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProfileBottomSheet(
    profileToEdit: Profile, // Ahora no es nulable, la pantalla se encarga de eso.
    onDismiss: () -> Unit,
    onSave: (id: String, name: String, url: String, user: String, pass: String, avatar: String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    val avatarOptions = listOf(
        "https://images.pexels.com/photos/8474443/pexels-photo-8474443.jpeg",
        "https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg",
        "https://images.pexels.com/photos/7210754/pexels-photo-7210754.jpeg",
        "https://images.pexels.com/photos/1851164/pexels-photo-1851164.jpeg",
        "https://images.pexels.com/photos/3767673/pexels-photo-3767673.jpeg",
        "https://images.pexels.com/photos/2379004/pexels-photo-2379004.jpeg"
    )

    var profileName by remember { mutableStateOf("") }
    var serverUrl by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedAvatar by remember { mutableStateOf(avatarOptions.first()) }

    LaunchedEffect(profileToEdit) {
        // Rellenamos los campos con los datos del perfil a editar
        profileName = profileToEdit.name
        serverUrl = profileToEdit.serverUrl
        username = profileToEdit.username
        password = profileToEdit.password
        // Si el avatar del perfil no está en las opciones, usamos el primero por defecto
        selectedAvatar = if (avatarOptions.contains(profileToEdit.avatarUrl)) profileToEdit.avatarUrl else avatarOptions.first()
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = null,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        modifier = Modifier
            .navigationBarsPadding()
            .padding(bottom = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Elige un avatar", style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(12.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(avatarOptions) { url ->
                    val isSelected = selectedAvatar == url
                    val shape = RoundedCornerShape(12.dp)
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(shape)
                            .clickable { selectedAvatar = url }
                            .border(
                                width = if (isSelected) 3.dp else 0.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = shape
                            )
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = url),
                            contentDescription = "Opción de avatar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            HorizontalDivider()
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = profileName,
                onValueChange = { profileName = it },
                label = { Text("Nombre del perfil") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = serverUrl,
                onValueChange = { serverUrl = it },
                label = { Text("Host o URL") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Usuario") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(24.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = {
                    scope.launch { sheetState.hide() }
                        .invokeOnCompletion { onDismiss() }
                }) {
                    Text("Cancelar")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        scope.launch { sheetState.hide() }
                            .invokeOnCompletion {
                                // CAMBIO CLAVE: Ya no genera un UUID.
                                // Simplemente pasa el ID que recibió (el real o el marcador).
                                // La responsabilidad de generar el ID ahora es 100% del ViewModel.
                                onSave(profileToEdit.id, profileName, serverUrl, username, password, selectedAvatar)
                            }
                    },
                    enabled = serverUrl.isNotBlank() && username.isNotBlank() && password.isNotBlank()
                ) {
                    Icon(Icons.Default.Save, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Guardar")
                }
            }
        }
    }
}