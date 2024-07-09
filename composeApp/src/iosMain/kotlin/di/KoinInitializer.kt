package di

import org.koin.core.context.startKoin
import org.koin.core.context.unloadKoinModules

actual class KoinInitializer {
    actual fun init(){
        startKoin {
            modules(appModule, viewModelModule, dataStoreModule, httpEngineModule)
        }
    }
}