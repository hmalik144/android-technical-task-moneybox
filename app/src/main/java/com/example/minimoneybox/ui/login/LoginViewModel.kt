package com.example.minimoneybox.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minimoneybox.data.repository.MoneyBoxRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class LoginViewModel(
    val repository: MoneyBoxRepository
) : ViewModel() {

    //stored @Nullable value for users name
    var name: String? = null

    //operation results livedata based on outcome of operation
    val operationSuccess = MutableLiveData<Boolean>()
    val operationFailed = MutableLiveData<String>()

    fun attemptLogin(
        email: String,
        password: String,
        usersName: String?
    ){
        //clear name before operation
        name = null
        //open a coroutine on the IO thread and run async operations
        //Network calls and secured shared preferences
        CoroutineScope(Dispatchers.Main).launch {
            try {
                //retrieve response from API call from login api network request
                val response = repository.loginUser(email, password)

                //null safety check to ensure bearer token exists
                response?.session?.bearerToken?.let {
                    //save data in secured prefs
                    repository.saveAuthToken(it)
                    repository.saveCredentials(email,password)

                    //update name in this viewmodel for later use
                    name = usersName

                    operationSuccess.postValue(true)
                    return@launch
                }
            }catch (exception: IOException){
                operationFailed.postValue(
                    exception.message ?: "could not receive token")
                return@launch
            }
            operationFailed.postValue("Could not login user")
        }
    }

}
