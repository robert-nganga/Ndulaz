package di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import core.data.createDataStore
import org.koin.dsl.module

actual val dataStoreModule = module {
    single<DataStore<Preferences>> { createDataStore()  }
}