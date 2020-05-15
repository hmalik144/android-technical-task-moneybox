package com.example.minimoneybox.ui.user.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.minimoneybox.R
import com.example.minimoneybox.data.models.ProductsResponse
import com.example.minimoneybox.utils.toCurrency
import kotlinx.android.synthetic.main.products_list_item.view.*
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * [ProductsResponse] recycler view adapter
 */
class ProductsAdapter(
    private val products: List<ProductsResponse>,
    private val recyclerClickListener: RecyclerClickListener
): Adapter<ProductsAdapter.ProductsViewHolder>() {

    class ProductsViewHolder(view: View) :
        ViewHolder(view) {
        var name = view.name_tv
        var planValue = view.plan_val_tv
        var moneyBoxVal = view.moneybox_val_tv
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProductsViewHolder {
        val view = LayoutInflater.from(p0.context)
            .inflate(R.layout.products_list_item, p0, false)

        return ProductsViewHolder(view)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(p0: ProductsViewHolder, p1: Int) {
        p0.name.text = products[p1].product?.friendlyName
        p0.planValue.text = products[p1].planValue?.toCurrency()
        p0.moneyBoxVal.text = products[p1].moneybox?.toCurrency()

        p0.itemView.setOnClickListener {
            recyclerClickListener.onItemSelected(p1)
        }
    }



}