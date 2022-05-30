package io.github.srthibaultp.projetnewsapi.data.internal

import io.github.srthibaultp.projetnewsapi.data.network.response.NewsAPIResponse
import io.github.srthibaultp.projetnewsapi.StatusAPI
import retrofit2.Response


inline fun safeApiCall(apiCall: () -> Response<NewsAPIResponse>): StatusAPI =
    try
    {
        val resultat = apiCall.invoke()
        if (resultat.isSuccessful)
        {
            val DonneesResultat = resultat.body()
            if (DonneesResultat != null && DonneesResultat.articles.isNotEmpty()) StatusAPI.Succes(DonneesResultat.articles)
            else StatusAPI.Empty
        }
        else StatusAPI.Erreur("Code d'erreur : ${resultat.code()} | Erreur : ${resultat.message()} - ${resultat.errorBody()}")

    }
    catch (e: Exception) { StatusAPI.Erreur(e.message ?: "Erreur inconnue") }
