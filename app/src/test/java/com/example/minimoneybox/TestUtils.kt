package com.example.minimoneybox

import androidx.lifecycle.LiveData
import com.example.minimoneybox.ui.OneTimeObserver

fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
    val observer = OneTimeObserver(handler = onChangeHandler)
    observe(observer, observer)
}