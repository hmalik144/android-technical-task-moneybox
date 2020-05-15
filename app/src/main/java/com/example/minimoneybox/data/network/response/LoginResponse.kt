package com.example.minimoneybox.data.network.response

import com.example.minimoneybox.data.models.SessionObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse(
    @SerializedName("Session")
    @Expose
    val session: SessionObject? = null
)