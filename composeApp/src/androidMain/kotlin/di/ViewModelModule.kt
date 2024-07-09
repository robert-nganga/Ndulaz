package di

import features.profile.presentation.screens.AuthViewModel
import features.shop.presentation.screens.home_screen.HomeScreenViewModel
import features.shop.presentation.screens.product_details_screen.ProductDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

actual val viewModelModule = module {
    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::ProductDetailsViewModel)
}