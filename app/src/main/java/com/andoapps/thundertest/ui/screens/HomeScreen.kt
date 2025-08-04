package com.andoapps.thundertest.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andoapps.thundertest.data.datasource.FakeData
import com.andoapps.thundertest.ui.components.ContentCarousel
import com.andoapps.thundertest.ui.components.FeaturedContent

@Composable
fun HomeScreen(paddingValues: PaddingValues) {


    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 110.dp, bottom = paddingValues.calculateBottomPadding())
        ) {
            item {
                FeaturedContent(item = FakeData.featuredContent)
            }
            item {
                ContentCarousel(title = "Juegos móviles", items = FakeData.mobileGames)
            }
            item {
                ContentCarousel(title = "Los 10 juegos móviles más populares", items = FakeData.popularMobileGames)
            }
            item {
                ContentCarousel(title = "Continuar viendo", items = FakeData.continueWatching)
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}