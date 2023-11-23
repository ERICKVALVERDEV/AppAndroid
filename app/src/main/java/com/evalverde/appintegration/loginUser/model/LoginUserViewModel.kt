package com.evalverde.appintegration.loginUser.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.evalverde.appintegration.globalModel.ResultViewModel

class LoginUserViewModel : ViewModel(){

    private val _loginResult = MutableLiveData<ResultViewModel>()
    val loginResult: LiveData<ResultViewModel> get() = _loginResult

    private val _userError = MutableLiveData<String>()
    val userError: LiveData<String> get() = _userError

    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> get()= _passwordError

    fun performLogin(usuario: String, clave: String){
        var isLoginSuccesful = true
        validateUser(usuario)
        validatePassword(clave)
        if(!usuario.isBlank() && !clave.isBlank()){
            isLoginSuccesful = (usuario == "admin" && clave == "123")
            _loginResult.value = ResultViewModel(isLoginSuccesful,"Credenciales incorrectas")
        }
    }

    fun validateUser(usuario: String){
        _userError.value = if(usuario.isBlank()) "Usuario es Obligatorio" else null
    }

    fun validatePassword(password :String){
        _passwordError.value = if(password.isBlank()) "Contraseña es Obligatorio" else null
    }

}