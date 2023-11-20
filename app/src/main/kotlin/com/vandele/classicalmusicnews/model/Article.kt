package com.vandele.classicalmusicnews.model

import java.time.Instant

data class Article(
    val author: String?,
    val id: String,
    val image: String?,
    val link: String?,
    val pubDate: Instant?,
    val title: String?,
    val rssSource: RssSource?,
    val isBookmarked: Boolean,
) {
    companion object {
        fun getPreview() = Article(
            author = "Vanessa Thorpe Arts and media correspondent",
            id = "https://www.theguardian.com/music/2023/nov/19/callas-centenary-the-real-maria-soprano",
            image = "https://i.guim.co.uk/img/media/d61f5ec01426d58a94e399ba67cdf2b31c378788/0_109_2430_1457/master/2430.jpg?width=140&quality=85&auto=format&fit=max&s=a53c1e3abd7926042461571c49fd274a",
            link = "https://www.theguardian.com/music/2023/nov/19/callas-centenary-the-real-maria-soprano",
            pubDate = Instant.parse("2023-11-19T09:15:45Z"),
            title = "‘They’ll see she was extraordinary’: Callas centenary inspires new generation to find the real Maria",
            rssSource = RssSource.TheGuardianOpera,
            isBookmarked = false,
        )
    }
}
