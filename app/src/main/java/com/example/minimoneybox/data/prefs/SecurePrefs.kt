package com.example.minimoneybox.data.prefs

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


class SecurePrefs(context: Context){

    val emailConstant = "EMAIL"
    val passwordConstant = "PASSWORD"
    val nameConstant = "NAME"
    val authToken = "AUTH"

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val prefs = EncryptedSharedPreferences.create(
        "encrypted_shared_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    @Synchronized
    fun saveCredentialsInPrefs(
        email: String,
        password: String
    ) {
        val editor = prefs.edit()
        editor.putString(emailConstant, email)
        editor.putString(passwordConstant, password)

        editor.apply()
    }

    @Synchronized
    fun loadCredentialsFromPrefs(): Pair<String,String> {
        val email = prefs.getString(emailConstant, "")
        val password = prefs.getString(passwordConstant, "")

        return Pair(email, password)
    }

    @Synchronized
    fun saveTokenInPrefs(
        token: String
    ) {
        val editor = prefs.edit()
        editor.putString(authToken, token)

        editor.apply()
    }

    @Synchronized
    fun loadTokenFromPrefs(): String? {
        return prefs.getString(authToken, "")
    }

}