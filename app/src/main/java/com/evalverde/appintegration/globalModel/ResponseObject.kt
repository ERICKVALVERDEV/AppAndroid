package com.evalverde.appintegration.globalModel

import com.google.gson.annotations.SerializedName

data class ResponseObject(@SerializedName("responseData") var JsonResponse: String,
                          @SerializedName("exception") var Exception: String)
