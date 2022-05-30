package io.github.srthibaultp.projetnewsapi.data.network.modele

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize

// https://newsapi.org/docs/endpoints/top-headlines
data class Article(
    val source: Source? = Source("", ""),
    val author: String? = "",
    val title: String? = "",
    val description: String? = "",
    @PrimaryKey val url: String = "",
    val urlToImage: String? = "",
    val publishedAt: String? = "",
    val content: String? = "",
    val isSaved: Boolean = false
) : Parcelable