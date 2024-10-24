package di

import core.data.HttpClientFactory
import core.data.preferences.DefaultSessionHandler
import core.data.preferences.DefaultUserPreferencesRepository
import core.data.preferences.SessionHandler
import core.data.preferences.UserPreferencesRepository
import core.domain.InputValidation
import features.profile.data.AuthRepositoryImpl
import features.profile.domain.repositories.AuthRepository
import features.shop.data.CartRepositoryImpl
import features.shop.data.LocationRepositoryImpl
import features.shop.data.OrderRepositoryImpl
import features.shop.data.ReviewRepositoryImpl
import features.shop.data.ShippingAddressRepositoryImpl
import features.shop.data.ShoesRepositoryImpl
import features.shop.data.UserRepositoryImpl
import features.shop.data.WishListRepositoryImpl
import features.shop.domain.repository.CartRepository
import features.shop.domain.repository.LocationRepository
import features.shop.domain.repository.OrderRepository
import features.shop.domain.repository.ReviewRepository
import features.shop.domain.repository.ShippingAddressRepository
import features.shop.domain.repository.ShoesRepository
import features.shop.domain.repository.UserRepository
import features.shop.domain.repository.WishListRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    single { InputValidation() }
    single { DefaultSessionHandler(dataStore = get()) }.bind<SessionHandler>()
    single { HttpClientFactory.createHttpClient(sessionHandler = get(), engine = get()) }
    single { AuthRepositoryImpl(httpClient = get(), sessionHandler = get()) }.bind<AuthRepository>()
    single { ShoesRepositoryImpl(httpClient = get(), database = get()) }.bind<ShoesRepository>()
    single { WishListRepositoryImpl(httpClient = get()) }.bind<WishListRepository>()
    single { CartRepositoryImpl(database = get()) }.bind<CartRepository>()
    single { LocationRepositoryImpl(httpClient = get()) }.bind<LocationRepository>()
    single { ShippingAddressRepositoryImpl(database = get()) }.bind<ShippingAddressRepository>()
    single { UserRepositoryImpl(httpClient = get(), sessionHandler = get()) }.bind<UserRepository>()
    single { OrderRepositoryImpl(httpClient = get()) }.bind<OrderRepository>()
    single { ReviewRepositoryImpl(httpClient = get()) }.bind<ReviewRepository>()
    single { DefaultUserPreferencesRepository(dataStore = get()) }.bind<UserPreferencesRepository>()

}