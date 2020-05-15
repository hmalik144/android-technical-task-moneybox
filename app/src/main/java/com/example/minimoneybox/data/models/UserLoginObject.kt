package com.example.minimoneybox.data.models

data class UserLoginObject(
    val Email: String,
    val Password: String,
    val Idfa: String = "ANYTHING"
)