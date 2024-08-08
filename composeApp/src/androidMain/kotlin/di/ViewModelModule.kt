package di

import features.profile.presentation.screens.AuthViewModel
import features.shop.presentation.screens.all_brands_screen.AllBrandsViewModel
import features.shop.presentation.screens.brand_screen.BrandScreenViewModel
import features.shop.presentation.screens.cart_screen.CartViewModel
import features.shop.presentation.screens.home_screen.HomeScreenViewModel
import features.shop.presentation.screens.most_popular_screen.MostPopularScreenViewModel
import features.shop.presentation.screens.product_details_screen.ProductDetailsViewModel
import features.shop.presentation.screens.wish_list_screen.WishListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

actual val viewModelModule = module {
    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::ProductDetailsViewModel)
    viewModelOf(::MostPopularScreenViewModel)
    viewModelOf(::BrandScreenViewModel)
    viewModelOf(::WishListViewModel)
    viewModelOf(::AllBrandsViewModel)
    viewModelOf(::CartViewModel)
}