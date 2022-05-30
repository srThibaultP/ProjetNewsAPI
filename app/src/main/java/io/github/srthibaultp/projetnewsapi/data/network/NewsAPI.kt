package io.github.srthibaultp.projetnewsapi.data.network

import io.github.srthibaultp.projetnewsapi.Constantes
import io.github.srthibaultp.projetnewsapi.data.network.response.NewsAPIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsAPI {

    // https://newsapi.org/docs/endpoints/top-headlines
    @GET("top-headlines")
    suspend fun getArticles(
        @Query("page") page: Int = Constantes.DEFAULT_PAGE,
        @Query("pageSize") pageSize: Int = Constantes.DEFAULT_PAGE_SIZE,
        @Query("country") countryCode: String = Constantes.DEFAULT_COUNTRY_CODE,
        @Query("apiKey") apiKey: String = Constantes.API_KEY
    ): Response<NewsAPIResponse>

    // https://newsapi.org/docs/endpoints/everything
    @GET("everything")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("page") page: Int = Constantes.DEFAULT_PAGE,
        @Query("pageSize") pageSize: Int = Constantes.DEFAULT_PAGE_SIZE,
        @Query("apiKey") apiKey: String = Constantes.API_KEY
    ): Response<NewsAPIResponse>
}