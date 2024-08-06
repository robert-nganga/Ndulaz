package di

import core.data.database.NdulaDatabase
import core.data.getDatabaseBuilder
import org.koin.dsl.module

actual val databaseModule = module {
    single<NdulaDatabase> { getDatabaseBuilder() }
}