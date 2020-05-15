package com.example.minimoneybox.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.minimoneybox.R
import com.example.minimoneybox.data.models.ProductsResponse
import com.example.minimoneybox.utils.show
import com.example.minimoneybox.utils.toCurrency
import kotlinx.android.synthetic.main.account_activity.*
import kotlinx.android.synthetic.main.investment_fragment.*
import kotlinx.android.synthetic.main.investment_fragment.view.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A Fragment showing product selected in [UserAccountsFragment].
 */
class CurrentInvestmentFragment : Fragment() {
    private var currentItemPosition: Int? = null

    private val accountActivity: AccountActivity by lazy {
        activity as AccountActivity
    }

    //grab viewmodel from the activity hosting fragment
    private val viewModel by lazy {
        accountActivity.viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentItemPosition = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.investment_fragment, container,
            false
        ).apply {
            //setup Observers after view instantiated
            setUpObserver()
        }
    }

    private fun setUpObserver(){
        viewModel.plans.observe(this, Observer { list ->
            //get list of products
            currentItemPosition?.let {
                populateViews(list[it])
            }
        })
    }

    //setting up views via kotlin synthetics
    //assign text and button on click
    private fun populateViews(currentProducts: ProductsResponse) {
        account_name_tv.text = currentProducts.product?.name
        friendly_account_tv.text = currentProducts.product?.friendlyName
        plan_val_tv.text = currentProducts.planValue?.toCurrency()
        moneybox_val_tv.text = currentProducts.moneybox?.toCurrency()
        btn_add_20.setOnClickListener {
            currentProducts.id?.let { id -> addOneOffPayment(id) }
        }
    }

    private fun addOneOffPayment(id: Int) {
        currentItemPosition?.let {
            accountActivity.progress_circular_account.show()
            viewModel.oneOffPayment(id)
        }
    }

    companion object {
        /**
         * fragment Instance using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment CurrentInvestmentFragment.
         */
        @JvmStatic
        fun newInstance(param1: Int) =
            CurrentInvestmentFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}
