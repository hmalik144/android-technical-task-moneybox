package com.example.minimoneybox.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minimoneybox.data.models.ProductsResponse
import com.example.minimoneybox.data.network.response.PaymentResponse
import com.example.minimoneybox.data.network.response.ProductApiResponse
import com.example.minimoneybox.data.repository.MoneyBoxRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class UserAccountsViewModel(
    val repository: MoneyBoxRepository
) : ViewModel() {

    var usersName: String? = null

    val totalPlanValue = MutableLiveData<Double>()
    val plans = MutableLiveData<List<ProductsResponse>>()

    //operation results livedata based on outcome of operation
    val operationSuccess = MutableLiveData<Boolean>()
    val operationFailed = MutableLiveData<String>()

    fun getInvestorProducts(){
        //retrieve auth token from safe storage/keystore
        //or finish operation
        val token = repository.loadAuthToken()
        if (token.isNullOrEmpty()){
            operationFailed.postValue("Failed to retrieve token")
            return
        }

        //open a coroutine on the IO thread and run async operations
        //Network calls and secured shared preferences
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.getProducts(token)?.let {
                    getProducts(it)
                    return@launch
                }
            }catch (exception: IOException){
                val credential = repository.loadCredentials()
                val loginResponse =
                    repository.loginUser(credential.first, credential.second)

                val code = loginResponse?.session?.bearerToken?.let {
                    repository.saveAuthToken(it)
                    it
                }

                //retrieve response from API call from login api network request
                val response = code?.let { repository.getProducts(it) }
                response?.let {
                    getProducts(it)
                    return@launch
                }
            }catch (exception: IOException){
                operationFailed.postValue(exception.message)
                return@launch
            }
            operationFailed.postValue("Could not retrieve products")
        }
    }

    fun oneOffPayment(productId: Int){
        //retrieve auth token from safe storage/keystore
        //or finish operation
        val token = repository.loadAuthToken()
        if (token.isNullOrEmpty()){
            operationFailed.postValue("Failed to retrieve token")
            return
        }

        //open a coroutine on the IO thread and run async operations
        //Network calls and secured shared preferences
        CoroutineScope(Dispatchers.IO).launch {
            try {
                //retrieve response from API call from for one off repayment
                repository.oneOffPayment(productId, token)?.let {
                    oneOffPaymentResponse(it)
                    return@launch
                }
            }catch (exception: IOException){
                val credential = repository.loadCredentials()
                val loginResponse =
                    repository.loginUser(credential.first, credential.second)

                val code = loginResponse?.session?.bearerToken?.let {
                    repository.saveAuthToken(it)
                    it
                }
                //retrieve response from API call from login api network request
                val response = code?.let {
                    repository.oneOffPayment(productId, code) }
                response?.let {
                    oneOffPaymentResponse(it)
                    return@launch
                }
            }catch (exception: IOException){
                operationFailed.postValue(exception.message)
                return@launch
            }
            operationFailed.postValue("Could not retrieve products")
        }
    }

    private fun getProducts(response: ProductApiResponse){
        response.let {
            //update livedata with relevent data
            totalPlanValue.postValue(it.totalPlanValue)
            plans.postValue(it.productsRespons)

            operationSuccess.postValue(true)
        }
    }

    private fun oneOffPaymentResponse(response: PaymentResponse){
        //null safety check to ensure bearer token exists
        response.moneybox?.let {
            getInvestorProducts()

            operationSuccess.postValue(true)
        }
    }

}
