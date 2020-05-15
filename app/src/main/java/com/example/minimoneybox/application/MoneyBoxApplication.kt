package com.example.minimoneybox.application

import android.app.Application
import com.example.minimoneybox.data.network.LoginApi
import com.example.minimoneybox.data.network.UserAccountApi
import com.example.minimoneybox.data.network.interceptors.HeaderInterceptor
import com.example.minimoneybox.data.network.interceptors.NetworkConnectionInterceptor
import com.example.minimoneybox.data.prefs.KeystoreStorage
import com.example.minimoneybox.data.repository.MoneyBoxRepositoryImpl
import com.example.minimoneybox.ui.login.LoginViewModelFactory
import com.example.minimoneybox.ui.user.UserAccountsViewModelFactory
import com.google.gson.Gson
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MoneyBoxApplication: Application(), KodeinAware{

    //Kodein aware to initialise the classes used for DI
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MoneyBoxApplication))

        //instance is context
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { HeaderInterceptor() }
        //instance is context
        bind() from singleton { KeystoreStorage(instance())  }
        //instances above 2 interceptors
        bind() from singleton { LoginApi(instance(), instance()) }
        bind() from singleton { UserAccountApi(instance(), instance()) }
        bind() from singleton { Gson() }
        //instances are context,
        bind() from singleton { MoneyBoxRepositoryImpl(instance(),instance(),instance(), instance()) }
        //Viewmodel created from context
        bind() from provider {
            LoginViewModelFactory(
                instance()
            )
        }
        bind() from provider {
            UserAccountsViewModelFactory(
                instance()
            )
        }

    }

}