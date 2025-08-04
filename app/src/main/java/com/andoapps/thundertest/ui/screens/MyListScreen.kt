package com.andoapps.thundertest.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.andoapps.thundertest.data.datasource.FakeData
import com.andoapps.thundertest.data.model.ContentItem

@Composable
fun MyListScreen(paddingValues: PaddingValues) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Mi lista", "Continuar viendo")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues) // El padding del Scaffold ya incluye el espacio del TopAppBar
            .background(Color.Black)
    ) {
        // El TopAppBar local ha sido eliminado de aquí.

        // Sistema de Pestañas
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Black,
            contentColor = Color.White
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(text = title, fontWeight = if(selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal) }
                )
            }
        }

        // Contenido principal que se desplaza
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp)
        ) {
            if (selectedTabIndex == 0) {
                items(FakeData.myListItems) { item ->
                    MyListItem(item = item) //
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Recomendado para ti",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                items(FakeData.recommendedItems.chunked(2)) { rowItems -> //
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { item ->
                            RecommendedGridItem(
                                item = item,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            } else {
                item {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Aquí iría el carrusel de 'Continuar viendo'",
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun MyListItem(item: ContentItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(item.imageUrl), //
            contentDescription = item.title, //
            modifier = Modifier
                .width(140.dp)
                .height(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, color = Color.White, fontSize = 16.sp) //
            item.year?.let {
                Text(text = it, color = Color.Gray, fontSize = 14.sp) //
            }
        }
        IconButton(onClick = { /* TODO: Mostrar menú */ }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Más opciones",
                tint = Color.White
            )
        }
    }
}

@Composable
fun RecommendedGridItem(item: ContentItem, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Image(
            painter = rememberAsyncImagePainter(item.imageUrl), //
            contentDescription = item.title, //
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.7f)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
}