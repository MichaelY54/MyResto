package com.example.myresto.restosapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.myresto.restosapp.data.local.RestoDao
import com.example.myresto.restosapp.data.local.RestoDb
import com.example.myresto.restosapp.data.remote.RestoranApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RestosModule {
    @Provides
    fun provideRoomDao(database: RestoDb): RestoDao{
        return database.dao
    }

    @Singleton
    @Provides
    fun provideRoomDb(
        @ApplicationContext appContext: Context
    ): RestoDb{
            return Room.databaseBuilder(appContext,
            RestoDb::class.java,
            "restaurants_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://realtimedb-3ef48-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .build()
    }

    @Provides
    fun provideRetrofitApi(retrofit: Retrofit) : RestoranApiService{
        return retrofit.create(RestoranApiService::class.java)
    }
}