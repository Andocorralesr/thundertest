// ruta: com/andoapps/thundertest/ui/screens/XtreamScreen.kt

package com.andoapps.thundertest.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Theaters
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.andoapps.thundertest.R
import com.andoapps.thundertest.data.model.Profile
import com.andoapps.thundertest.ui.components.ActionButton
import com.andoapps.thundertest.ui.components.AddProfileBottomSheet
import com.andoapps.thundertest.ui.components.ProfileItem
import com.andoapps.thundertest.ui.screens.xtream.XtreamViewModel

@Composable
fun XtreamScreen(
    paddingValues: PaddingValues,
    onProfileClick: (Profile) -> Unit,
    viewModel: XtreamViewModel = hiltViewModel()
) {
    val profiles by viewModel.profiles.collectAsState()
    var isEditMode by remember { mutableStateOf(false) }
    var profileToEdit by remember { mutableStateOf<Profile?>(null) }

    // CORRECCIÓN 3: Se elimina la variable 'showAddSheet' porque ya no se usa.

    // CORRECCIÓN 1: Crear una copia local para evitar el error de 'Smart cast impossible'.
    val currentProfileToEdit = profileToEdit

    // El BottomSheet se muestra si la copia local del perfil no es nula.
    if (currentProfileToEdit != null) {
        AddProfileBottomSheet(
            profileToEdit = currentProfileToEdit, // Usamos la copia local segura.
            onDismiss = {
                profileToEdit = null
            },
            onSave = { id, name, url, user, pass, avatar ->
                Log.d("XtreamScreen", "Enviando perfil al ViewModel con ID: $id")
                viewModel.addOrUpdateProfile(id, name, url, user, pass, avatar)
                profileToEdit = null
                isEditMode = false
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = rememberAsyncImagePainter(
                model = "https://images.pexels.com/photos/1117132/pexels-photo-1117132.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                placeholder = painterResource(id = R.drawable.placeholder_image),
                error = painterResource(id = R.drawable.error_image)
            ),
            contentDescription = "Imagen de fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Black.copy(alpha = 0.4f), Color.Black),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.6f))

            Icon(
                imageVector = Icons.Filled.Theaters,
                contentDescription = "Logo de la app",
                tint = Color.White.copy(alpha = 0.9f),
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text("Elige tu perfil", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(24.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.padding(horizontal = 48.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(profiles) { profile ->
                    ProfileItem(
                        profile = profile,
                        isEditMode = isEditMode,
                        onClick = {
                            if (isEditMode) {
                                profileToEdit = profile
                            } else {
                                onProfileClick(profile)
                            }
                        }
                    )
                }
                item {
                    ActionButton(
                        icon = Icons.Default.Add,
                        label = "Agregar",
                        onClick = {
                            isEditMode = false
                            // CORRECCIÓN 2: El constructor de Profile tiene 6 argumentos, no 7.
                            profileToEdit = Profile("new_profile_marker", "", "", "", "", "")
                        }
                    )
                }
                item {
                    ActionButton(
                        icon = if (isEditMode) Icons.Default.Done else Icons.Default.Edit,
                        label = if (isEditMode) "Finalizar" else "Editar",
                        onClick = { isEditMode = !isEditMode }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}