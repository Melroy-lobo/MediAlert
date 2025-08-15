package com.melroy.medialert.di

import android.app.Application
import androidx.room.Room
import com.melroy.medialert.data.db.AppDb
import com.melroy.medialert.data.db.MedDao
import com.melroy.medialert.util.AlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides @Singleton
    fun provideDb(app: Application): AppDb =
        Room.databaseBuilder(app, AppDb::class.java, "medialert.db").build()

    @Provides
    fun provideMedDao(db: AppDb): MedDao = db.medDao()

    @Provides @Singleton
    fun provideAlarmScheduler(app: Application): AlarmScheduler = AlarmScheduler(app)
}