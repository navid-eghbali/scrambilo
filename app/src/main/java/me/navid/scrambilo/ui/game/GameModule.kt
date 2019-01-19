package me.navid.scrambilo.ui.game

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import me.navid.scrambilo.di.FragmentScoped
import me.navid.scrambilo.di.ViewModelKey

@Module
internal abstract class GameModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeGameFragment(): GameFragment

    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel::class)
    abstract fun bindGameViewModel(viewModel: GameViewModel): ViewModel
}