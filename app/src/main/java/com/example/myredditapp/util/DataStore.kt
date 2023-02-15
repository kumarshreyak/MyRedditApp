package com.example.myredditapp.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Component
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

suspend fun <T : Any> DataStore<Preferences>.writeData(key: Preferences.Key<T>, value: T) {
    edit { prefs ->
        prefs[key] = value
    }
}

suspend fun <T : Any> DataStore<Preferences>.readData(key: Preferences.Key<T>) {
    data
        .catch { e ->
            e.printStackTrace()
            emit(emptyPreferences())
        }.map { prefs ->
            prefs[key]
        }.firstOrNull()
}

const val ACCESS_TOKEN = "ACCESS_TOKEN"


