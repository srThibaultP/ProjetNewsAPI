package io.github.srthibaultp.projetnewsapi.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import io.github.srthibaultp.projetnewsapi.Constantes
import io.github.srthibaultp.projetnewsapi.data.network.modele.Article
import io.github.srthibaultp.projetnewsapi.data.network.modele.Source


@Database(entities = [Article::class], version = Constantes.DATABASE_VERSION, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() { abstract fun dao(): ArticleCommandes }

class Converter
{
    @TypeConverter
    fun convertFromSource(source: Source): String = source.name

    @TypeConverter
    fun convertToSource(sourceName: String): Source = Source(sourceName, sourceName)
}