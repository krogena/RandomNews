package ru.iyshcherbakov.randomnews.data.api


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.iyshcherbakov.randomnews.models.NewsResponse
import ru.iyshcherbakov.randomnews.utils.Constants.Companion.API_KEY

interface NewsService {
    @GET("/v2/everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY,
        ) : Response<NewsResponse>

    @GET("/v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country") countryCode: String = "ru",
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY,
    ): Response<NewsResponse>
}