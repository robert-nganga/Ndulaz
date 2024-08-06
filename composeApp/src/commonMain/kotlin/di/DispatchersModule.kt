package di

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dispatcherModule = module {
    single(named(Dispatcher.IO)) { Dispatcher.IO }
    single(named(Dispatcher.MAIN)) { Dispatchers.Main }
    single(named(Dispatcher.DEFAULT)) { Dispatchers.Default }
    single(named(Dispatcher.UNCONFINED)) { Dispatchers.Unconfined }
}

enum class Dispatcher {
    IO,
    MAIN,
    DEFAULT,
    UNCONFINED,
}