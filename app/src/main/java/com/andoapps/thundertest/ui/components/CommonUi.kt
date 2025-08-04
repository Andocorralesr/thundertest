package com.andoapps.thundertest.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.andoapps.thundertest.data.model.ContentItem

@Composable
fun ContentCarousel(title: String, items: List<ContentItem>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items) { item -> ContentPoster(item = item) }
        }
    }
}

@Composable
fun ChannelPoster(item: ContentItem, modifier: Modifier = Modifier) {
    Column(
        modifier           = modifier.width(90.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier        = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(40.dp)) // círculo
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            if (item.imageUrl.isNotBlank()) {
                Image(
                    painter           = rememberAsyncImagePainter(item.imageUrl),
                    contentDescription = item.title,
                    contentScale      = ContentScale.Crop,
                    modifier          = Modifier.fillMaxSize()
                )
            } else {
                Text(
                    text       = item.title.first().toString(),
                    color      = Color.White,
                    fontSize   = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Text(
            text      = item.title,
            color     = Color.White,
            fontSize  = 12.sp,
            maxLines  = 1,
            textAlign = TextAlign.Center,
            modifier  = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun ContentPoster(item: ContentItem, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.width(110.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            // Centramos el contenido del Box por defecto
            contentAlignment = Alignment.Center
        ) {
            // --- INICIO DE LA LÓGICA CONDICIONAL ---
            if (item.imageUrl.isNotBlank()) {
                // Si hay URL, mostramos la imagen
                Image(
                    painter = rememberAsyncImagePainter(item.imageUrl),
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                if (item.top10) {
                    Top10Badge(modifier = Modifier.align(Alignment.TopEnd))
                }
            } else {
                // Si NO hay URL, creamos un placeholder con el título
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.DarkGray), // Fondo para el placeholder
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.title,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            // --- FIN DE LA LÓGICA CONDICIONAL ---
        }

        // El título debajo del póster se mantiene igual
        Text(
            text = item.title,
            color = Color.White,
            fontSize = 13.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}

@Composable
fun Top10Badge(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(4.dp)
            .background(Color(0xFFE50914), shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(text = "TOP 10", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold)
    }
}