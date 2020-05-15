package com.example.minimoneybox.ui.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.minimoneybox.R
import com.example.minimoneybox.utils.displayToast
import com.example.minimoneybox.utils.hide
import com.example.minimoneybox.utils.setAnimation
import com.example.minimoneybox.utils.show
import kotlinx.android.synthetic.main.account_activity.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
/**
 * Activity holding the fragments for products.
 */
class AccountActivity : AppCompatActivity(), KodeinAware {

    //retrieve the viewmodel factory from the kodein dependency injection
    override val kodein by kodein()
    private val factory : UserAccountsViewModelFactory by instance()

    val accountFragmentManager: FragmentManager by lazy {
        supportFragmentManager
    }

    //to be used by the fragments
    lateinit var viewModel: UserAccountsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_activity)

        viewModel = ViewModelProviders
            .of(this , factory).get(UserAccountsViewModel::class.java)
        intent.extras?.getString("NAME_USER").let {
            viewModel.usersName = it
        }

        if (savedInstanceState == null) {
            accountFragmentManager.beginTransaction()
                .replace(R.id.container, UserAccountsFragment())
                .setAnimation()
                .commit()
        }

        fetchData()
        setupOperationObservers()
    }

    //override back button
    //conditional back pressing
    override fun onBackPressed() {
        if(accountFragmentManager.backStackEntryCount > 0){
            accountFragmentManager.popBackStack()
        }else{
            super.onBackPressed()
        }
    }

    private fun fetchData(){
        progress_circular_account.show()
        viewModel.getInvestorProducts()
    }

    private fun setupOperationObservers(){
        viewModel.operationSuccess.observe(this, Observer {
            progress_circular_account.hide()
        })
        viewModel.operationFailed.observe(this, Observer {
            displayToast(it)
            progress_circular_account.hide()
        })
    }
}

