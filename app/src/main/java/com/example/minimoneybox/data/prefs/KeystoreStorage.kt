package com.example.minimoneybox.data.prefs

import android.content.Context
import devliving.online.securedpreferencestore.DefaultRecoveryHandler
import devliving.online.securedpreferencestore.SecuredPreferenceStore

const val emailConstant = "EMAIL"
const val passwordConstant = "PASSWORD"
const val authToken = "AUTH"

/**
 * Secured preferences class storing values in keystore
 * inclusive of version down to [android.os.Build.VERSION_CODES.LOLLIPOP]
 */
class KeystoreStorage(context: Context) {

    //lazy initialization
    private val prefStore: SecuredPreferenceStore by lazy {
        SecuredPreferenceStore.getSharedInstance()
    }

    //init block to create instance of
    init {
        val storeFileName = "securedStore"
        val keyPrefix = "mba"
        val seedKey = "SecuredSeedData".toByteArray()
        SecuredPreferenceStore.init(
            context.applicationContext,
            storeFileName,
            keyPrefix,
            seedKey,
            DefaultRecoveryHandler()
        )
    }

    @Synchronized
    fun saveCredentialsInPrefs(
        email: String,
        password: String
    ) {
        val editor = prefStore.edit()
        editor.putString(emailConstant, email)
        editor.putString(passwordConstant, password)

        editor.apply()
    }

    @Synchronized
    fun loadCredentialsFromPrefs(): Pair<String, String> {
        val email = prefStore.getString(emailConstant, "")
        val password = prefStore.getString(passwordConstant, "")

        return Pair(email, password)
    }

    @Synchronized
    fun saveTokenInPrefs(
        token: String
    ) {
        val editor = prefStore.edit()
        editor.putString(authToken, token)

        editor.apply()
    }

    @Synchronized
    fun loadTokenFromPrefs(): String? {
        return prefStore.getString(authToken, "")
    }

}