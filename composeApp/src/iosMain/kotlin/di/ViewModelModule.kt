package di

import features.profile.presentation.screens.AuthViewModel
import features.shop.presentation.screens.home_screen.HomeScreenViewModel
import features.shop.presentation.screens.product_details_screen.ProductDetailsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val viewModelModule = module {
    singleOf(::AuthViewModel)
    singleOf(::HomeScreenViewModel)
    singleOf(::ProductDetailsViewModel)

}