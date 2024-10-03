package core.data.preferences

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val USER_JSON = stringPreferencesKey("user_json")
    val THEME = stringPreferencesKey("theme")
}