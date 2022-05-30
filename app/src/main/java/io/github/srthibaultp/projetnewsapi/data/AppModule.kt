package io.github.srthibaultp.projetnewsapi.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.srthibaultp.projetnewsapi.Constantes
import io.github.srthibaultp.projetnewsapi.data.db.AppDatabase
import io.github.srthibaultp.projetnewsapi.data.db.ArticleCommandes
import io.github.srthibaultp.projetnewsapi.data.network.NewsAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule
{
    @Provides
    @Singleton
    fun provideNewsAPI(): NewsAPI =
        Retrofit.Builder()
            .baseUrl(Constantes.URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPI::class.java)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, Constantes.DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDAO(db: AppDatabase): ArticleCommandes = db.dao()
}