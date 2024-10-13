package com.ru.hazakura.data.di

import android.app.Application
import android.content.Context
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ru.hazakura.data.database.anime.AnimeDataBase
import com.ru.hazakura.data.datastore.ProtoDataStoreManager
import com.ru.hazakura.data.network.KodikApi
import com.ru.hazakura.data.network.KodikJsonApi
import com.ru.hazakura.data.network.KodikStringApi
import com.ru.hazakura.data.network.ShikiApi
import com.ru.hazakura.data.network.repository.AnimeRepoisitoryImpl
import com.ru.hazakura.data.network.repository.KodikJsonRepositoryImpl
import com.ru.hazakura.data.network.repository.KodikRepositoryImpl
import com.ru.hazakura.data.network.repository.KodikStringRepositoryImpl
import com.ru.hazakura.data.network.repository.ShikiRepositoryImpl
import com.ru.hazakura.domain.repository.AnimeRepository
import com.ru.hazakura.domain.repository.KodikJsonRepository
import com.ru.hazakura.domain.repository.KodikRepository
import com.ru.hazakura.domain.repository.KodikStringRepository
import com.ru.hazakura.domain.repository.ShikiRepository
import com.ru.hazakura.util.DecompressionInterceptor
import com.ru.hazakura.util.Migration1To2
import com.ru.hazakura.util.Migration2To3
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .setLenient()
            .create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(DecompressionInterceptor())
            .build()

    @Provides
    @Singleton
    fun provideShikiApi(gson: Gson, okHttpClient: OkHttpClient): ShikiApi =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(ShikiApi.baseUrl)
            .build()
            .create(ShikiApi::class.java)

    @Provides
    @Singleton
    fun provideKodikApi(gson: Gson, okHttpClient: OkHttpClient): KodikApi =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(KodikApi.baseUrl)
            .build()
            .create(KodikApi::class.java)

    @Provides
    @Singleton
    fun provideKodikStringApi(okHttpClient: OkHttpClient): KodikStringApi =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(KodikStringApi.baseUrl)
            .build()
            .create(KodikStringApi::class.java)

    @Provides
    @Singleton
    fun provideKodikJsonApi(gson: Gson,okHttpClient: OkHttpClient): KodikJsonApi =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(KodikJsonApi.baseUrl)
            .build()
            .create(KodikJsonApi::class.java)

    @Provides
    @Singleton
    fun provideKodikJsonRepository(api: KodikJsonApi): KodikJsonRepository =
        KodikJsonRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideKodikStringRepository(api: KodikStringApi, kodikJsonRepository: KodikJsonRepository): KodikStringRepository =
        KodikStringRepositoryImpl(api,kodikJsonRepository)

    @Provides
    @Singleton
    fun provideKodikRepository(api: KodikApi): KodikRepository =
        KodikRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideShikiRepository(api: ShikiApi): ShikiRepository =
        ShikiRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideProtoDataStoreManager(@ApplicationContext context: Context): ProtoDataStoreManager =
        ProtoDataStoreManager(context)

    @Provides
    @Singleton
    fun providesAnimeDataBase(app: Application): AnimeDataBase {
        return try {
            Room.databaseBuilder(
                app,
                AnimeDataBase::class.java,
                "moviedb.db"
            )
                .addMigrations(Migration1To2())// Добавляем миграцию
                .addMigrations(Migration2To3())
                .build()
        } catch (e: Exception) {
            throw RuntimeException("Error creating or getting database", e)
        }
    }


    @Provides
    @Singleton
    fun provideAnimeRepoisitory(animeDataBase : AnimeDataBase): AnimeRepository =
        AnimeRepoisitoryImpl(animeDataBase)
}

@Module
@InstallIn(ViewModelComponent::class)
object VideoPlayerModule {

    @Provides
    @ViewModelScoped
    fun provideVideoPlayer(app: Application): Player {
        return ExoPlayer.Builder(app)
            .build()
    }
}