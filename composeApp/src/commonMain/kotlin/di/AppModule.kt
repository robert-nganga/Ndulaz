package di

import core.data.HttpClientFactory
import core.data.TokenProvider
import core.data.preferences.DefaultTokenProvider
import core.domain.InputValidation
import org.koin.dsl.module

val appModule = module {

    single { InputValidation() }
    single<TokenProvider> { DefaultTokenProvider(dataStore = get()) }
    single { HttpClientFactory.createHttpClient(tokenProvider = get()) }

}