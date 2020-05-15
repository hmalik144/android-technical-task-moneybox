package com.example.minimoneybox.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.minimoneybox.data.repository.MoneyBoxRepositoryImpl

/**
 * Viewmodel factory for [UserAccountsViewModel]
 * @repository injected into MainViewModel
 */
@Suppress("UNCHECKED_CAST")
class UserAccountsViewModelFactory(
    private val repositoryImpl: MoneyBoxRepositoryImpl
): ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserAccountsViewModel(
            repositoryImpl
        ) as T
    }
}