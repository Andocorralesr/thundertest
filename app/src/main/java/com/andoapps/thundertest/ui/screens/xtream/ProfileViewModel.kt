// ruta: com/andoapps/thundertest/ui/screens/xtream/ProfileViewModel.kt
package com.andoapps.thundertest.ui.screens.xtream

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andoapps.thundertest.data.model.XtreamContentItem
import com.andoapps.thundertest.data.repository.ProfileRepository
import com.andoapps.thundertest.data.repository.XtreamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val xtreamRepository: XtreamRepository,
    private val profileRepository: ProfileRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val profileId: String = savedStateHandle.get<String>("profileId")!!

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadAllContent()
    }

    private fun loadAllContent() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val profile = profileRepository.getProfileById(profileId)

            if (profile == null) {
                _uiState.update { it.copy(isLoading = false, error = "Perfil no encontrado") }
                return@launch
            }

            try {
                val base = profile.serverUrl
                val user = profile.username
                val pass = profile.password

                val liveCategoriesDeferred = async { xtreamRepository.getLiveCategories(base, user, pass) }
                val liveStreamsDeferred = async { xtreamRepository.getLiveStreams(base, user, pass) }
                val vodCategoriesDeferred = async { xtreamRepository.getVodCategories(base, user, pass) }
                val vodsDeferred = async { xtreamRepository.getVodStreams(base, user, pass) }
                val seriesCategoriesDeferred = async { xtreamRepository.getSeriesCategories(base, user, pass) }
                val seriesDeferred = async { xtreamRepository.getSeries(base, user, pass) }

                // Mapeamos cada lista a nuestro modelo unificado
                val allContent = (liveStreamsDeferred.await().map { XtreamContentItem.Live(it) } +
                        vodsDeferred.await().map { XtreamContentItem.Vod(it) } +
                        seriesDeferred.await().map { XtreamContentItem.Series(it) })
                    .shuffled() // Opcional: mezcla el contenido

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        profileName = profile.name,
                        allContent = allContent, // <-- NUEVA LISTA UNIFICADA
                        liveCategories = liveCategoriesDeferred.await(),
                        vodCategories = vodCategoriesDeferred.await(),
                        seriesCategories = seriesCategoriesDeferred.await(),
                        error = null
                    )
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Error al cargar: ${e.message}") }
            }
        }
    }

    fun onToggleFavorite(itemId: String, itemType: String) {
        viewModelScope.launch {
            // Lógica para guardar favoritos en la base de datos local
        }
    }
}