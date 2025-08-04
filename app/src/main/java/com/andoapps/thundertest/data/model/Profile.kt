package com.andoapps.thundertest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "profiles") // Le damos un nombre a la tabla
data class Profile(
    @PrimaryKey val id: String, // Indicamos que 'id' es la clave primaria
    val name: String,
    val avatarUrl: String,
    val serverUrl: String,
    val username: String,
    val password: String
)