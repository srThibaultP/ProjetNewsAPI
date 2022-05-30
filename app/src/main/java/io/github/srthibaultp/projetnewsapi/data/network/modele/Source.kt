package io.github.srthibaultp.projetnewsapi.data.network.modele

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    val id: String, //Ne pas utiliser int
    val name: String
) : Parcelable