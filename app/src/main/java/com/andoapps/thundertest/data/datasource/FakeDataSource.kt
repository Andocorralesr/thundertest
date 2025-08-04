package com.andoapps.thundertest.data.datasource

import com.andoapps.thundertest.data.model.ContentItem

object FakeData {
    private fun getImageUrl(seed: String, width: Int = 400, height: Int = 600) =
        "https://picsum.photos/seed/$seed/$width/$height"

    val featuredContent = ContentItem(
        id = 1,
        title = "Merlina",
        imageUrl = "https://image.tmdb.org/t/p/w780/9PFonBhy4cQy7Jz20NpMygczOkv.jpg",
        genres = listOf("Sarcástico", "Escalofriante", "Serie de humor negro")
    )

    val mobileGames = listOf(
        ContentItem(id = 100, title = "Solitario", imageUrl = getImageUrl("solitario"), isMovie = false, top10 = true),
        ContentItem(id = 101, title = "GTA: San Andreas", imageUrl = getImageUrl("gta"), isMovie = false, top10 = true),
        ContentItem(id = 102, title = "Football Manager 2024", imageUrl = getImageUrl("football"), isMovie = false, top10 = true),
        ContentItem(id = 103, title = "El juego de calamares", imageUrl = getImageUrl("calamar"), isMovie = false, top10 = true),
    )

    val popularMobileGames = List(10) {
        ContentItem(id = it + 200, title = "Popular Game ${it + 1}", imageUrl = getImageUrl("popgame${it}"))
    }

    val continueWatching = listOf(
        ContentItem(id = 300, title = "Gladiador", imageUrl = getImageUrl("gladiador"), top10 = true),
        ContentItem(id = 301, title = "Blacklist", imageUrl = getImageUrl("blacklist")),
        ContentItem(id = 302, title = "Sandman", imageUrl = getImageUrl("sandman"), top10 = true),
    )

    val myListItems = listOf(
        ContentItem(id = 400, title = "Mountainhead", imageUrl = getImageUrl("mountainhead"), year = "2025"),
        ContentItem(id = 401, title = "Como Agua para Chocolate", imageUrl = getImageUrl("chocolate"), year = "1992"),
        ContentItem(id = 402, title = "El Misterio de Salem's Lot", imageUrl = getImageUrl("salem"), year = "2024")
    )

    val recommendedItems = listOf(
        ContentItem(id = 500, title = "Midnight Sun", imageUrl = getImageUrl("midnightsun", 400, 600)),
        ContentItem(id = 501, title = "She Said He Said", imageUrl = getImageUrl("shesaid", 400, 600)),
        ContentItem(id = 502, title = "The Creator", imageUrl = getImageUrl("creator", 400, 600)),
        ContentItem(id = 503, title = "Dune: Part Two", imageUrl = getImageUrl("dune2", 400, 600))
    )
}
