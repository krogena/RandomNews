package ru.iyshcherbakov.randomnews.data.api

import retrofit2.http.Query
import ru.iyshcherbakov.randomnews.data.db.ArticleDao
import ru.iyshcherbakov.randomnews.models.Article
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsService: NewsService,
    private val articleDao: ArticleDao) {
    suspend fun getNews(countryCode: String, pageNumber: Int) =
        newsService.getHeadlines(countryCode = countryCode, page = pageNumber)

    suspend fun getSearchNews(query: String, pageNumber: Int) =
        newsService.getEverything(query = query, page = pageNumber)

    fun getFavouriteArticle() = articleDao.getAllArticles()
    suspend fun addToFavourite(article: Article) = articleDao.insert(article = article)
    suspend fun deleteToFavourite(article: Article) = articleDao.delete(article = article)
}