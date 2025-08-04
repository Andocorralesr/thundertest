package com.andoapps.thundertest.ui.screens.xtream

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andoapps.thundertest.data.model.Profile
import com.andoapps.thundertest.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class XtreamViewModel @Inject constructor(private val repository: ProfileRepository) : ViewModel() {

    val profiles: StateFlow<List<Profile>> = repository.allProfiles.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun addOrUpdateProfile(id: String, name: String, serverUrl: String, username: String, password: String, avatarUrl: String) {
        viewModelScope.launch {
            val finalName = name.ifBlank { "Perfil" }
            val finalId = if (id.isBlank() || id == "new_profile_marker") UUID.randomUUID().toString() else id
            val profile = Profile(finalId, finalName, avatarUrl, serverUrl, username, password)
            repository.addOrUpdateProfile(profile)
        }
    }

    fun deleteProfile(profileId: String) {
        viewModelScope.launch {
            repository.deleteProfile(profileId)
        }
    }
}