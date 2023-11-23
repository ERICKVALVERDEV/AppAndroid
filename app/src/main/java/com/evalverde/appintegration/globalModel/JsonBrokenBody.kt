package com.evalverde.appintegration.globalModel

import com.google.gson.annotations.SerializedName

data class JsonBrokenBody(@SerializedName("credentials") var Credentials: String,
                          @SerializedName("containerKey") var ContainerKey: String,
                          @SerializedName("targetComponent") var TargetComponent: String,
                          @SerializedName("method") var Method: String,
                          @SerializedName("args") var Args: String){
    constructor() : this("", "", "", "", "")
}
