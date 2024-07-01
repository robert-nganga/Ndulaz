package di

import core.domain.InputValidation
import org.koin.dsl.module

val appModule = module {

    single { InputValidation() }

}