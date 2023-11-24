package com.evalverde.appintegration.globalModel

import com.google.gson.annotations.SerializedName

data class ExceptionResponse(@SerializedName("type") var Type: String,
                             @SerializedName("message") var Message: String,
                             @SerializedName("details") var Details: String,)
