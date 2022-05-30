package io.github.srthibaultp.projetnewsapi.data.network.response

import com.google.gson.annotations.SerializedName
import io.github.srthibaultp.projetnewsapi.data.network.modele.Article

data class NewsAPIResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)