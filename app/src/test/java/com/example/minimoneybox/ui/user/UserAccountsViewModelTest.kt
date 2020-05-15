package com.example.minimoneybox.ui.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.minimoneybox.data.network.response.PaymentResponse
import com.example.minimoneybox.data.network.response.ProductApiResponse
import com.example.minimoneybox.data.repository.MoneyBoxRepository
import com.example.minimoneybox.observeOnce
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import java.io.IOException

class UserAccountsViewModelTest{
    // Run tasks synchronously
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: UserAccountsViewModel

    @Mock
    lateinit var repository: MoneyBoxRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = UserAccountsViewModel(repository)
    }

    @Test
    fun getProducts_validCredentials_successResponse() = runBlocking{
        //GIVEN
        val token = "123456"
        val product = mock(ProductApiResponse::class.java)

        //WHEN
        Mockito.`when`(repository.loadAuthToken()).thenReturn(token)
        Mockito.`when`(repository.getProducts(token)).thenReturn(product)

        //THEN
        viewModel.getInvestorProducts()
        viewModel.operationSuccess.observeOnce {
            assertEquals(true, it)
        }
    }

    @Test
    fun getProducts_validCredentials_unsuccessfulResponse() = runBlocking{
        //GIVEN
        val token = "123456"
        val errorMessage = "Could not retrieve products"

        //WHEN
        Mockito.`when`(repository.loadAuthToken()).thenReturn(token)
        Mockito.`when`(repository.getProducts(token)).thenAnswer { throw IOException(errorMessage) }

        //THEN
        viewModel.getInvestorProducts()
        viewModel.operationFailed.observeOnce {
            assertEquals(errorMessage, it)
        }
    }

    @Test
    fun getProducts_invalidCredentials_unsuccessfulResponse() = runBlocking{
        //GIVEN
        val errorMessage = "Failed to retrieve token"

        //WHEN
        Mockito.`when`(repository.loadAuthToken()).thenReturn(null)

        //THEN
        viewModel.getInvestorProducts()
        viewModel.operationFailed.observeOnce {
            assertEquals(errorMessage, it)
        }
    }

    @Test
    fun oneOffPayment_validCredentials_successResponse() = runBlocking{
        //GIVEN
        val productId = 2020
        val token = "123456"
        val product = mock(PaymentResponse::class.java)

        //WHEN
        Mockito.`when`(repository.loadAuthToken()).thenReturn(token)
        Mockito.`when`(repository.oneOffPayment(productId,token)).thenReturn(product)

        //THEN
        viewModel.oneOffPayment(2020)
        viewModel.operationSuccess.observeOnce {
            assertEquals(true, it)
        }
    }

    @Test
    fun oneOffPayment_validCredentials_unsuccessfulResponse() = runBlocking{
        //GIVEN
        val productId = 2020
        val token = "123456"
        val errorMessage = "Could not post payment"

        //WHEN
        Mockito.`when`(repository.loadAuthToken()).thenReturn(token)
        Mockito.`when`(repository.oneOffPayment(productId,token)).thenAnswer { throw IOException(errorMessage)}

        //THEN
        viewModel.oneOffPayment(2020)
        viewModel.operationSuccess.observeOnce {
            assertEquals(false, it)
        }
        viewModel.operationFailed.observeOnce {
            print(it)
            assertEquals(errorMessage, it)
        }
    }

    @Test
    fun oneOffPayment_invalidCredentials_unsuccessfulResponse() = runBlocking{
        //GIVEN
        val errorMessage = "Failed to retrieve token"
        val token = ""
        val productId = 2020

        //WHEN
        Mockito.`when`(repository.oneOffPayment(productId, token)).thenReturn(null)

        //THEN
        viewModel.oneOffPayment(productId)
        viewModel.operationFailed.observeOnce {
            assertEquals(errorMessage, it)
        }
    }
}