package dev.luisbaena.prodentclient.data.local

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "prodent_user_prefs")

@Singleton
class UserPreferences @Inject constructor(private val context: Context) {

    companion object {
       //TODO terminar
    }







}
