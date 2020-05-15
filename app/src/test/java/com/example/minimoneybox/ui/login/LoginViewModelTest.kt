package com.example.minimoneybox.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.minimoneybox.data.models.SessionObject
import com.example.minimoneybox.data.network.response.LoginResponse
import com.example.minimoneybox.data.repository.MoneyBoxRepository
import com.example.minimoneybox.observeOnce
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.IOException


class LoginViewModelTest {

    // Run tasks synchronously
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: LoginViewModel

    @Mock
    lateinit var repository: MoneyBoxRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = LoginViewModel(repository)
    }

    @Test
    fun attemptLogin_validCredentials_successResponse() = runBlocking{
        //GIVEN
        val sessionObject = SessionObject()
        sessionObject.bearerToken = "123456"
        val loginResponse = LoginResponse(sessionObject)

        //WHEN
        Mockito.`when`(repository.loginUser("email","password"))
            .thenReturn(loginResponse)

        //THEN
        viewModel.operationSuccess.observeOnce {
            print(it.toString())
            assertEquals(true, it)
        }
    }

    @Test
    fun attemptLogin_validCredentials_unsuccessfulResponse() = runBlocking{
        //GIVEN
        val errorMessage = "Could not login user"
        //WHEN
        Mockito.`when`(repository.loginUser("email","password"))
            .thenAnswer { throw IOException() }

        //THEN
        viewModel.operationFailed.observeOnce {
            assertEquals(errorMessage, it)
        }
    }
}