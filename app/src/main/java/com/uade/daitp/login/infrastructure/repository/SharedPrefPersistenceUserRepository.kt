package com.uade.daitp.login.infrastructure.repository

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.uade.daitp.Cinemapp
import com.uade.daitp.login.core.model.User

class SharedPrefPersistenceUserRepository : PersistenceUserRepository {

    private val sharedPreferences: SharedPreferences
    private var gson = Gson()

    init {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        sharedPreferences = EncryptedSharedPreferences.create(
            "preferences",
            masterKeyAlias,
            Cinemapp.getAppContext(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun saveUser(user: User) {
        sharedPreferences.edit().putString(userKey, gson.toJson(user)).apply()
    }

    override fun getUser(): User {
        val user = sharedPreferences.getString(userKey, null)
        return gson.fromJson(user, User::class.java)
    }

    override fun saveToken(token: String) {
        sharedPreferences.edit().putString(userToken, token).apply()
    }

    override fun getToken(): String {
        return sharedPreferences.getString(userToken, "")!!
    }

    override fun getBearerToken(): String {
        return "Bearer ${getToken()}"
    }

    override fun removeUser() {
        sharedPreferences.edit()
            .remove(userKey)
            .remove(userToken)
            .apply()
    }

    private companion object {
        const val userKey = "saved_user"
        const val userToken = "saved_token"
    }
}