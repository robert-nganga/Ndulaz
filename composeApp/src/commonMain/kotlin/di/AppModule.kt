package di

import core.data.HttpClientFactory
import core.data.preferences.DefaultSessionHandler
import core.data.preferences.SessionHandler
import core.domain.InputValidation
import features.profile.data.AuthRepositoryImpl
import features.profile.domain.repositories.AuthRepository
import features.shop.data.CartRepositoryImpl
import features.shop.data.ShoesRepositoryImpl
import features.shop.data.WishListRepositoryImpl
import features.shop.domain.repository.CartRepository
import features.shop.domain.repository.ShoesRepository
import features.shop.domain.repository.WishListRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    single { InputValidation() }
    single { DefaultSessionHandler(dataStore = get()) }.bind<SessionHandler>()
    single { HttpClientFactory.createHttpClient(sessionHandler = get(), engine = get()) }
    single { AuthRepositoryImpl(httpClient = get(), sessionHandler = get()) }.bind<AuthRepository>()
    single { ShoesRepositoryImpl(httpClient = get()) }.bind<ShoesRepository>()
    single { WishListRepositoryImpl(httpClient = get()) }.bind<WishListRepository>()
    single { CartRepositoryImpl(database = get()) }.bind<CartRepository>()

}