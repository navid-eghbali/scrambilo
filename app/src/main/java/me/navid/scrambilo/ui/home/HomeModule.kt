package me.navid.scrambilo.ui.home

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.navid.scrambilo.di.FragmentScoped

@Module
internal abstract class HomeModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contrinuteHomeFragment(): HomeFragment
}