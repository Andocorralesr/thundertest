package com.andoapps.thundertest.data.repository

import com.andoapps.thundertest.data.local.dao.ProfileDao
import com.andoapps.thundertest.data.model.Profile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val profileDao: ProfileDao
) {
    val allProfiles: Flow<List<Profile>> = profileDao.getAllProfiles()

    suspend fun getProfileById(profileId: String): Profile? {
        return profileDao.getProfileById(profileId)
    }

    suspend fun addOrUpdateProfile(profile: Profile) {
        profileDao.insertOrUpdateProfile(profile)
    }

    suspend fun deleteProfile(profileId: String) {
        profileDao.deleteProfileById(profileId)
    }
}