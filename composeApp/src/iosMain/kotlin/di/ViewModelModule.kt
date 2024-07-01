package di

import features.profile.presentation.screens.AuthViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val viewModelModule = module {
    singleOf(::AuthViewModel)
}