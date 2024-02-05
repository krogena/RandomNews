package ru.iyshcherbakov.randomnews.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.iyshcherbakov.randomnews.data.api.NewsRepository
import ru.iyshcherbakov.randomnews.models.Article
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    init {
        getSavedArticles()
    }
    fun getSavedArticles() = viewModelScope.launch(Dispatchers.IO) {
        repository.getFavouriteArticle()
    }

    fun saveFavouriteArticles(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        repository.addToFavourite(article = article)
    }
}