package di

import features.profile.presentation.screens.AuthViewModel
import features.shop.presentation.screens.add_location_screen.AddLocationViewModel
import features.shop.presentation.screens.all_brands_screen.AllBrandsViewModel
import features.shop.presentation.screens.brand_screen.BrandScreenViewModel
import features.shop.presentation.screens.cart_screen.CartViewModel
import features.shop.presentation.screens.check_out_screen.CheckOutViewModel
import features.shop.presentation.screens.edit_profile_screen.EditProfileViewModel
import features.shop.presentation.screens.home_screen.HomeScreenViewModel
import features.shop.presentation.screens.most_popular_screen.MostPopularScreenViewModel
import features.shop.presentation.screens.orders_screen.OrdersViewModel
import features.shop.presentation.screens.product_details_screen.ProductDetailsViewModel
import features.shop.presentation.screens.profile_screen.ProfileViewModel
import features.shop.presentation.screens.review_screen.ReviewViewModel
import features.shop.presentation.screens.search_screen.SearchViewModel
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
    viewModelOf(::SearchViewModel)
    viewModelOf(::CheckOutViewModel)
    viewModelOf(::AddLocationViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::EditProfileViewModel)
    viewModelOf(::OrdersViewModel)
    viewModelOf(::ReviewViewModel)
}