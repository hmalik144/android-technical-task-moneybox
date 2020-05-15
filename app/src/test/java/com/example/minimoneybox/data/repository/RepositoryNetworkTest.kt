package com.example.minimoneybox.data.repository

import com.example.minimoneybox.data.network.LoginApi
import com.example.minimoneybox.data.network.UserAccountApi
import com.example.minimoneybox.data.network.response.LoginResponse
import com.example.minimoneybox.data.network.response.PaymentResponse
import com.example.minimoneybox.data.network.response.ProductApiResponse
import com.example.minimoneybox.data.prefs.KeystoreStorage
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.io.IOException
import kotlin.test.assertFailsWith


class RepositoryNetworkTest{

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
    fun loginUser_positiveResponse() = runBlocking {
        //GIVEN - Create the hashmap used in the function
        val hash = HashMap<String, String>()
        hash["Email"] = "email"
        hash["Password"] = "password"
        hash["Idfa"] = "ANYTHING"
        //create a successful retrofit response
        val mockLoginResponse = mock(LoginResponse::class.java)
        val re = Response.success(mockLoginResponse)

        //WHEN - loginApiRequest to return a successful response
        Mockito.`when`(api.loginApiRequest(hash)).thenReturn(re)

        //THEN - the unwrapped login response contains the correct values
        val login = repository.loginUser("email","password")
        assertNotNull(login)
        assertEquals(login, mockLoginResponse)
    }

    @Test
    fun getInvestmentProducts_positiveResponse() = runBlocking {
        //GIVEN
        val mockProductsResponse = mock(ProductApiResponse::class.java)
        val re = Response.success(mockProductsResponse)

        //WHEN
        Mockito.`when`(userAccountApi.getProductsFromApi("Bearer ")).thenReturn(re)

        //THEN
        val products = repository.getProducts("")
        assertNotNull(products)
        assertEquals(products, mockProductsResponse)
    }

    @Test
    fun pushOneOffPayment_positiveResponse() = runBlocking {
        //GIVEN
        val hash = HashMap<String, Int>()
        hash["Amount"] = 20
        hash["InvestorProductId"] = 1674
        val mockPaymentsResponse = mock(PaymentResponse::class.java)
        val re = Response.success(mockPaymentsResponse)

        //WHEN
        Mockito.`when`(userAccountApi.oneOffPaymentsFromApi(hash, "Bearer 133nhdk12l58dmHJBNmd=")).thenReturn(re)

        //THEN
        val products = repository.oneOffPayment(1674,"133nhdk12l58dmHJBNmd=")
        assertNotNull(products)
        assertEquals(products, mockPaymentsResponse)
    }

    @Test
    fun loginUser_negativeResponse() = runBlocking {
        //GIVEN
        val hash = HashMap<String, String>()
        hash["Email"] = "email"
        hash["Password"] = "password"
        hash["Idfa"] = "ANYTHING"
        //mock retrofit error response
        val mockBody = mock(ResponseBody::class.java)
        val mockRaw = mock(okhttp3.Response::class.java)
        val re = Response.error<String>(mockBody, mockRaw)

        //WHEN
        Mockito.`when`(api.loginApiRequest(hash)).then { re }

        //THEN - assert exception is not null
        val ioExceptionReturned= assertFailsWith<IOException> {
            repository.loginUser("email","password")
        }
        assertNotNull(ioExceptionReturned)
        assertNotNull(ioExceptionReturned.message)
    }

    @Test
    fun getInvestmentProducts_negativeResponse() = runBlocking {
        //GIVEN
        val mockBody = mock(ResponseBody::class.java)
        val mockRaw = mock(okhttp3.Response::class.java)
        val re = Response.error<String>(mockBody, mockRaw)

        //WHEN
        Mockito.`when`(userAccountApi.getProductsFromApi("Bearer ")).then { re }

        //THEN
        val ioExceptionReturned= assertFailsWith<IOException> {
            repository.getProducts("")
        }

        assertNotNull(ioExceptionReturned)
        assertNotNull(ioExceptionReturned.message)
    }

    @Test
    fun pushOneOffPayment_negativeResponse() = runBlocking {
        //GIVEN
        val hash = HashMap<String, Int>()
        hash["Amount"] = 20
        hash["InvestorProductId"] = 1674

        val mockBody = mock(ResponseBody::class.java)
        val mockRaw = mock(okhttp3.Response::class.java)
        val re = Response.error<String>(mockBody, mockRaw)

        //WHEN
        Mockito.`when`(userAccountApi.oneOffPaymentsFromApi(hash, "Bearer ")).then { re }

        //THEN
        val ioExceptionReturned= assertFailsWith<IOException> {
            repository.oneOffPayment(1674,"")
        }

        assertNotNull(ioExceptionReturned)
        assertNotNull(ioExceptionReturned.message)
    }

}