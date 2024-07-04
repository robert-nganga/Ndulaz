package di

import features.profile.presentation.screens.AuthViewModel
import features.shop.presentation.screens.home_screen.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

actual val viewModelModule = module {
    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeScreenViewModel)

}