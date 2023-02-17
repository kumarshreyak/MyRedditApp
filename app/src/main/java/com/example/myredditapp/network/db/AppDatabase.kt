package com.example.myredditapp.network.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myredditapp.network.models.PokemonList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Database(entities = [PokemonList.PokemonItem::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getHomeRoomDataSource(): HomeRoomDataSource
}

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideHomeRoomDataSource(appDatabase: AppDatabase): HomeRoomDataSource {
        return appDatabase.getHomeRoomDataSource()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "pokemon_db"
        ).build()
    }


}