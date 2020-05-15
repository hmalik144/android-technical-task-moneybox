package com.example.minimoneybox.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class SessionObject {
    @SerializedName("BearerToken")
    @Expose
    var bearerToken: String? = null
    @SerializedName("ExternalSessionId")
    @Expose
    var externalSessionId: String? = null
    @SerializedName("SessionExternalId")
    @Expose
    var sessionExternalId: String? = null
    @SerializedName("ExpiryInSeconds")
    @Expose
    var expiryInSeconds: Int? = null
}
