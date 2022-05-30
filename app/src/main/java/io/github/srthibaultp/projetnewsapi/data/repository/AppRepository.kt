package io.github.srthibaultp.projetnewsapi.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.srthibaultp.projetnewsapi.Constantes
import io.github.srthibaultp.projetnewsapi.data.db.ArticleCommandes
import io.github.srthibaultp.projetnewsapi.data.internal.ArticlePagingSource
import io.github.srthibaultp.projetnewsapi.data.internal.safeApiCall
import io.github.srthibaultp.projetnewsapi.data.network.NewsAPI
import io.github.srthibaultp.projetnewsapi.data.network.modele.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppRepository @Inject constructor(private val api: NewsAPI, private val dao: ArticleCommandes)
{

    suspend fun getBreakingNewsArticles(): Flow<PagingData<Article>>
    {
        return Pager(
            config = generatePagingConfig(),
            pagingSourceFactory = { ArticlePagingSource { position, loadSize -> safeApiCall { api.getArticles(position, loadSize) } } }
        ).flow
    }

    fun searchArticles(query: String): Flow<PagingData<Article>>
    {
        return Pager(
            config = generatePagingConfig(),
            pagingSourceFactory =  { ArticlePagingSource { position, size -> safeApiCall { api.searchArticles(query, position, size) } } }
        ).flow
    }

    private fun generatePagingConfig() = PagingConfig(
        maxSize = Constantes.DEFAULT_MAX_SIZE,
        pageSize = Constantes.DEFAULT_PAGE_SIZE,
        enablePlaceholders = false
    )


    fun getSavedArticles() = dao.getSavedArticles()
    suspend fun insert(article: Article) = dao.insert(article)
    suspend fun delete(article: Article) = dao.delete(article)
}