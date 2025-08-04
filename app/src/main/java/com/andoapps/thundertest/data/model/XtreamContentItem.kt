// ruta: com/andoapps/thundertest/data/model/XtreamContentItem.kt
package com.andoapps.thundertest.data.model

sealed class XtreamContentItem {
    abstract val id: String
    abstract val name: String?
    abstract val imageUrl: String?
    abstract val categoryId: String?

    data class Live(val channel: M3UChannel) : XtreamContentItem() {
        // Corrección: Usar channel.id en lugar de channel.tvgId
        override val id: String get() = channel.id // <-- CORRECCIÓN AQUÍ
        override val name: String get() = channel.name // name en M3UChannel no es nullable, pero aquí sí por la abstracción.
        override val imageUrl: String? get() = channel.logo
        override val categoryId: String? get() = channel.group
    }

    data class Vod(val vod: XtreamVod) : XtreamContentItem() {
        override val id: String get() = vod.streamId.toString()
        override val name: String? get() = vod.name
        override val imageUrl: String? get() = vod.streamIcon
        override val categoryId: String? get() = vod.categoryId
    }

    data class Series(val series: XtreamSeries) : XtreamContentItem() {
        override val id: String get() = series.seriesId.toString()
        override val name: String? get() = series.name
        override val imageUrl: String? get() = series.cover
        override val categoryId: String? get() = series.categoryId
    }
}