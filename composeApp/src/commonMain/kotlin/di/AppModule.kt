package di

import core.data.HttpClientFactory
import core.data.preferences.DefaultSessionHandler
import core.data.preferences.SessionHandler
import core.domain.InputValidation
import org.koin.dsl.module

val appModule = module {

    single { InputValidation() }
    single<SessionHandler> { DefaultSessionHandler(dataStore = get()) }
    single { HttpClientFactory.createHttpClient(sessionHandler = get()) }

}