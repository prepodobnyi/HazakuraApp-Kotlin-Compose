package com.ru.hazakura.data.datastore

import AccessAndRefreshTokenSerializer
import android.content.Context
import androidx.datastore.dataStore
import com.ru.hazakura.domain.model.AccessAndRefreshToken

private val Context.protoDataStore by dataStore("AccessAndRefreshToken.json", AccessAndRefreshTokenSerializer)
class ProtoDataStoreManager(
    private val context: Context
) {
    suspend fun saveTokens(tokens: AccessAndRefreshToken){
        context.protoDataStore.updateData {
            tokens
        }
    }
    fun getTokens() = context.protoDataStore.data
}