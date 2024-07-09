package di

import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val httpEngineModule = module {
    single { Darwin.create() }
}