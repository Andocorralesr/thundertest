package com.andoapps.thundertest.data.remote

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

/**
 * Este adaptador personalizado maneja el campo 'rating' de la API,
 * que a veces llega como un número (Double) y a veces como un texto (String).
 */
class RatingAdapter {

    // Esta función se ejecuta al convertir de JSON a un objeto Kotlin
    @FromJson
    fun fromJson(reader: com.squareup.moshi.JsonReader): Double? {
        return when (reader.peek()) {
            // Si el valor es un NÚMERO, lo leemos como Double.
            com.squareup.moshi.JsonReader.Token.NUMBER -> reader.nextDouble()
            // Si el valor es un TEXTO, lo leemos como String y lo convertimos a Double.
            // Si la conversión falla (ej. el texto es "N/A"), devuelve null.
            com.squareup.moshi.JsonReader.Token.STRING -> reader.nextString().toDoubleOrNull()
            // Si el valor es nulo o de otro tipo, lo ignoramos y devolvemos null.
            else -> {
                reader.skipValue()
                null
            }
        }
    }

    // Esta función se ejecuta al convertir un objeto Kotlin a JSON (no la usaremos, pero es bueno tenerla)
    @ToJson
    fun toJson(writer: com.squareup.moshi.JsonWriter, value: Double?) {
        writer.value(value)
    }
}