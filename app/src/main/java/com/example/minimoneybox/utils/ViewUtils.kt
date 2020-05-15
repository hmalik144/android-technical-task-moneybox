package com.example.minimoneybox.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.hide(){
    this.visibility = View.GONE
}

fun Context.displayToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun FragmentTransaction.setAnimation() = apply {
    setCustomAnimations(
        android.R.anim.fade_in,
        android.R.anim.fade_out,
        android.R.anim.fade_in,
        android.R.anim.fade_out
    )
}