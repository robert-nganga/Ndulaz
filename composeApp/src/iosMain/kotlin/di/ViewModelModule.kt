package di

import features.profile.presentation.screens.AuthViewModel
import features.shop.presentation.screens.add_location_screen.AddLocationViewModel
import features.shop.presentation.screens.all_brands_screen.AllBrandsViewModel
import features.shop.presentation.screens.brand_screen.BrandScreenViewModel
import features.shop.presentation.screens.cart_screen.CartViewModel
import features.shop.presentation.screens.check_out_screen.CheckOutViewModel
import features.shop.presentation.screens.home_screen.HomeScreenViewModel
import features.shop.presentation.screens.most_popular_screen.MostPopularScreenViewModel
import features.shop.presentation.screens.product_details_screen.ProductDetailsViewModel
import features.shop.presentation.screens.search_screen.SearchViewModel
import features.shop.presentation.screens.wish_list_screen.WishListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val viewModelModule = module {
    singleOf(::AuthViewModel)
    singleOf(::HomeScreenViewModel)
    singleOf(::ProductDetailsViewModel)
    singleOf(::MostPopularScreenViewModel)
    singleOf(::BrandScreenViewModel)
    singleOf(::WishListViewModel)
    singleOf(::AllBrandsViewModel)
    singleOf(::CartViewModel)
    singleOf(::SearchViewModel)
    singleOf(::CheckOutViewModel)
    singleOf(::AddLocationViewModel)
}