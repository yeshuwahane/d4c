package com.yeshuwahane.d4c.data.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton






@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        val JWT_TOKEN = stringPreferencesKey("jwt_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val IS_EXISTING_USER = booleanPreferencesKey("is_existing_user")
    }

    private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = { File(context.filesDir, "user_prefs.preferences_pb") }
    )

    suspend fun saveTokens(jwt: String, refreshToken: String, isExistingUser: Boolean) {
        println("🔐 TokenManager - Saving JWT: $jwt")
        dataStore.edit { preferences ->
            preferences[JWT_TOKEN] = jwt
            preferences[REFRESH_TOKEN] = refreshToken
            preferences[IS_EXISTING_USER] = isExistingUser
        }
    }


    val jwtTokenFlow: Flow<String?> = dataStore.data.map { it[JWT_TOKEN] }
    val refreshTokenFlow: Flow<String?> = dataStore.data.map { it[REFRESH_TOKEN] }
    val isExistingUserFlow: Flow<Boolean?> = dataStore.data.map { it[IS_EXISTING_USER] }

    suspend fun clearTokens() {
        dataStore.edit {
            it.remove(JWT_TOKEN)
            it.remove(REFRESH_TOKEN)
            it.remove(IS_EXISTING_USER)
        }
    }
}
