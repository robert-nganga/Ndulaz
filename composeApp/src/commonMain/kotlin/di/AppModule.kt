package di

import core.data.HttpClientFactory
import core.data.preferences.DefaultSessionHandler
import core.data.preferences.SessionHandler
import core.domain.InputValidation
import features.profile.data.AuthRepositoryImpl
import features.profile.domain.repositories.AuthRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    single { InputValidation() }
    single { DefaultSessionHandler(dataStore = get()) }.bind<SessionHandler>()
    single { HttpClientFactory.createHttpClient(sessionHandler = get()) }
    single { AuthRepositoryImpl(httpClient = get(), sessionHandler = get()) }.bind<AuthRepository>()

}