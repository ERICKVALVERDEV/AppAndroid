package com.evalverde.appintegration.globalModel

import com.google.gson.annotations.SerializedName

data class ResponseBroker(@SerializedName("exception") var Exception: ExceptionResponse,
                          @SerializedName("responseData") var JsonResponse: String)
