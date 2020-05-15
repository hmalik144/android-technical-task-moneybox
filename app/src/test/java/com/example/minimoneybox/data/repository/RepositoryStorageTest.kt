package com.example.minimoneybox.data.repository

import com.example.minimoneybox.data.network.LoginApi
import com.example.minimoneybox.data.network.UserAccountApi
import com.example.minimoneybox.data.prefs.KeystoreStorage
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RepositoryStorageTest {

    lateinit var repository: MoneyBoxRepository

    @Mock
    lateinit var api: LoginApi
    @Mock
    lateinit var securePrefs: KeystoreStorage
    @Mock
    lateinit var userAccountApi: UserAccountApi

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = MoneyBoxRepositoryImpl(securePrefs, api, userAccountApi, Gson())
    }

    @Test
    fun saveAndRetrieve_PositiveResponse() {
        //GIVEN
        val token = "92RG9qYRq5sUUaoJuolORfZORb9G6n014X2I="
        repository.saveAuthToken(token)

        //WHEN
        Mockito.`when`(securePrefs.loadTokenFromPrefs()).thenReturn(token)

        //THEN
        assertEquals(token, repository.loadAuthToken())
    }

    @Test
    fun saveAndRetrieveCredentials_PositiveResponse() {
        //GIVEN
        val pair: Pair<String, String> = Pair("forename", "Surname")
        repository.saveCredentials("forename", "Surname")

        //WHEN
        Mockito.`when`(securePrefs.loadCredentialsFromPrefs()).thenReturn(pair)

        //THEN
        assertEquals(pair, repository.loadCredentials())
    }
}