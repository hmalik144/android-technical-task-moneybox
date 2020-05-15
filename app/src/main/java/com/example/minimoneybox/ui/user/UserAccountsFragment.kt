package com.example.minimoneybox.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minimoneybox.R
import com.example.minimoneybox.ui.user.recyclerview.ProductsAdapter
import com.example.minimoneybox.ui.user.recyclerview.RecyclerClickListener
import com.example.minimoneybox.utils.setAnimation
import com.example.minimoneybox.utils.toCurrency
import kotlinx.android.synthetic.main.user_accounts_fragment.*

/**
 * A Fragment showing the first overview of products list.
 */
class UserAccountsFragment : Fragment(),
    RecyclerClickListener {

    private val viewModel by lazy {
        (activity as AccountActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.user_accounts_fragment, container, false
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupDataObservers()
        setupName()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        products_recycler_view.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupName() {
        val name = viewModel.usersName

        user_name_tv.text = if (name.isNullOrEmpty()) {
            "Hello !"
        } else {
            "Hello $name !"
        }
    }

    private fun setupDataObservers() {
        viewModel.totalPlanValue.observe(this, Observer { planValue ->
            total_plan_val_tv.text = getTotalString(planValue)
        })
        viewModel.plans.observe(this, Observer {
            ProductsAdapter(
                it, this).apply {
                products_recycler_view.adapter = this
                notifyDataSetChanged()
            }
        })
    }

    private fun getTotalString(planValue: Double?): CharSequence? {
        return StringBuilder()
            .append("Total Plan: ")
            .append(planValue?.toCurrency())
            .toString()
    }

    override fun onItemSelected(position: Int) {
        (activity as AccountActivity).accountFragmentManager
            .beginTransaction()
            .replace(
                R.id.container,
                CurrentInvestmentFragment.newInstance(position)
            )
            .setAnimation()
            .addToBackStack("CurrentInvestment")
            .commit()
    }

}
