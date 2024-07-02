package di

import core.data.HttpClientFactory
import core.domain.InputValidation
import org.koin.dsl.module

val appModule = module {

    single { InputValidation() }
    single { HttpClientFactory.createHttpClient("") }

}