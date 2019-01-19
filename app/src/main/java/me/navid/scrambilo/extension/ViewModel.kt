package me.navid.scrambilo.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider(provider: ViewModelProvider.Factory) =
    ViewModelProviders.of(this, provider)[VM::class.java]

inline fun <reified VM : ViewModel> Fragment.viewModelProvider(provider: ViewModelProvider.Factory) =
    ViewModelProviders.of(this, provider)[VM::class.java]

inline fun <reified VM : ViewModel> Fragment.activityViewModelProvider(provider: ViewModelProvider.Factory) =
    ViewModelProviders.of(requireActivity(), provider)[VM::class.java]

inline fun <reified VM : ViewModel> Fragment.parentViewModelProvider(provider: ViewModelProvider.Factory) =
    ViewModelProviders.of(parentFragment!!, provider)[VM::class.java]