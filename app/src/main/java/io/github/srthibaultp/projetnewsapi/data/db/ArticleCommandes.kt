package io.github.srthibaultp.projetnewsapi.data.db


import androidx.room.*
import io.github.srthibaultp.projetnewsapi.data.network.modele.Article
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticleCommandes
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Delete
    suspend fun delete(article: Article)

    @Query("SELECT * FROM article")
    fun getSavedArticles(): Flow<List<Article>>
}