package io.github.srthibaultp.projetnewsapi.ihm.news

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.srthibaultp.projetnewsapi.data.network.modele.Article
import io.github.srthibaultp.projetnewsapi.data.repository.AppRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: AppRepository,
    state: SavedStateHandle
) : ViewModel()
{
    private val _articles = MutableLiveData<PagingData<Article>>()
    val articles: LiveData<PagingData<Article>> = _articles

    val currentQuery: MutableLiveData<String> = state.getLiveData(KEY_CURRENT_QUERY, "")

    init { currentQuery.observeForever { newQuery -> getAllArticles(newQuery) } }


    fun searchArticlesPaging(query: String) { currentQuery.value = query }

    private fun getAllArticles(newQuery: String) = viewModelScope.launch {
        if (newQuery.isNotEmpty())  repository.searchArticles(newQuery).cachedIn(viewModelScope).collect { _articles.value = it }
        else repository.getBreakingNewsArticles().cachedIn(viewModelScope).collect { _articles.value = it }
    }

    fun saveArticleClicked(article: Article) = viewModelScope.launch {  repository.insert(article.copy(isSaved = true)) }

    companion object { private const val KEY_CURRENT_QUERY = "key_current_query" }
}