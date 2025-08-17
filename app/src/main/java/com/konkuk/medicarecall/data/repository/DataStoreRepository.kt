package com.konkuk.medicarecall.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = "tokens")

// Token 값을 불러오려면 HiltViewModel 에서
// DataStoreRepository를 받아
// getToken() 함수를 호출하면 됩니다.
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {


    companion object {
        val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }

    suspend fun saveAccessToken(token: String) {
        context.tokenDataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = token
        }
    }

    suspend fun getAccessToken(): String? {
        val preferences = context.tokenDataStore.data.first()
        return preferences[ACCESS_TOKEN_KEY]
    }

    suspend fun saveRefreshToken(token: String) {
        context.tokenDataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = token
        }
    }

    suspend fun getRefreshToken(): String? {
        val preferences = context.tokenDataStore.data.first()
        return preferences[REFRESH_TOKEN_KEY]
    }

    suspend fun clearTokens() {
        context.tokenDataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN_KEY)
            prefs.remove(REFRESH_TOKEN_KEY)
        }
    }

}