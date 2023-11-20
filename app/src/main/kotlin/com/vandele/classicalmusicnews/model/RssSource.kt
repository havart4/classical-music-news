package com.vandele.classicalmusicnews.model

import androidx.annotation.StringRes
import com.vandele.classicalmusicnews.R

sealed class RssSource(
    val id: String,
    @StringRes val nameRes: Int,
    val link: String,
) {
    data object ClassicalMusicWithBigMike : RssSource(
        id = "ClassicalMusicWithBigMike",
        nameRes = R.string.source_classical_music_with_big_mike,
        link = "https://classicalmusicwithbigmike.com/feed/",
    )

    data object MyScena : RssSource(
        id = "MyScena",
        nameRes = R.string.source_my_scena,
        link = "https://myscena.org/classical-music/feed/",
    )

    data object Naxos : RssSource(
        id = "Naxos",
        nameRes = R.string.source_naxos,
        link = "https://blog.naxos.com/feed",
    )

    data object TheGuardianOpera : RssSource(
        id = "TheGuardianOpera",
        nameRes = R.string.source_the_guardian_opera,
        link = "https://theguardian.com/music/opera/rss",
    )

    data object TheListenersClub : RssSource(
        id = "TheListenersClub",
        nameRes = R.string.source_the_listeners_club,
        link = "https://thelistenersclub.com/feed/",
    )

    companion object {
        fun values() =
            listOf(ClassicalMusicWithBigMike, MyScena, Naxos, TheGuardianOpera, TheListenersClub)

        fun fromId(rssSourceId: String) = values().firstOrNull { it.id == rssSourceId }
    }
}
