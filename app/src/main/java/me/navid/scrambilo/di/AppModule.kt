package me.navid.scrambilo.di

import android.content.Context
import dagger.Module
import dagger.Provides
import me.navid.scrambilo.MyApplication

@Module
class AppModule {
    @Provides
    fun provideContext(application: MyApplication): Context = application.applicationContext
}