package io.github.srthibaultp.projetnewsapi

import io.github.srthibaultp.projetnewsapi.data.network.modele.Article


sealed class StatusAPI
{
    class Succes(val data: List<Article>) : StatusAPI()
    class Erreur(val message: String) : StatusAPI()
    object Empty : StatusAPI()
}