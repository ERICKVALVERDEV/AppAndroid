package com.evalverde.appintegration.loginApp.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.evalverde.appintegration.DataAccess.Repository.UsuarioLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
@HiltViewModel
class LoginAppViewModel  constructor(private val usuarioLocalRepository: UsuarioLocalRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> get() = _loginResult

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
            _loginResult.value = LoginResult(isLoginSuccesful,"Credenciales incorrectas")
        }
    }

    fun validateUser(usuario: String){
        _userError.value = if(usuario.isBlank()) "Usuario es Obligatorio" else null
    }

    fun validatePassword(password :String){
        _passwordError.value = if(password.isBlank()) "Contraseña es Obligatorio" else null
    }

}