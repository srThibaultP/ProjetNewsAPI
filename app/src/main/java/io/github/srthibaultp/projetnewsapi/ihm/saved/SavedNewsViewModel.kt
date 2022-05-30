package io.github.srthibaultp.projetnewsapi.ihm.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.srthibaultp.projetnewsapi.data.network.modele.Article
import io.github.srthibaultp.projetnewsapi.data.repository.AppRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel @Inject constructor(private val repository: AppRepository) : ViewModel()
{
    fun getSavedArticles() = repository.getSavedArticles().asLiveData()
    fun deleteArticle(article: Article) = viewModelScope.launch { repository.delete(article) }
    fun saveArticle(article: Article) = viewModelScope.launch { repository.insert(article.copy(isSaved = true)) }
}