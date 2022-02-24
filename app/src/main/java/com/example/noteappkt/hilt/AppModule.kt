package com.example.noteappkt.hilt

import android.app.Application
import androidx.room.Room
import com.example.noteappkt.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
     fun provideDatabase(application:Application):AppDatabase=
         Room.databaseBuilder(application,AppDatabase::class.java,"Note_Database").build()

}