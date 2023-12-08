package com.evalverde.appintegration.globalModel

data class ResultViewModel(val isValid: Boolean,
                           var responseObject: Any?,
                           var errorText: String? = null)
