package com.evalverde.appintegration.ui.loginUser.model

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evalverde.appintegration.globalModel.ResultViewModel
import com.evalverde.appintegration.onlineClient.GenUsuarioClientOperation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginUserViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<ResultViewModel>()
    val loginResult: LiveData<ResultViewModel> get() = _loginResult

    private val _userError = MutableLiveData<String>()
    val userError: LiveData<String> get() = _userError

    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> get() = _passwordError

    fun performLogin(usuario: String, clave: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var isLoginSuccessful = false
//                validateUser(usuario)
//                validatePassword(clave)
                if (!usuario.isBlank() && !clave.isBlank()) {
                    var err: String? = null
                    try {
                        val user = GenUsuarioClientOperation().LoginSession(usuario, clave)
                        isLoginSuccessful = (user.CodigoUsuario == usuario)
                    } catch (ex: Exception) {
                        err = ex.message.toString()
                    }
                    // Actualizar LiveData en el hilo principal
                    withContext(Dispatchers.Main) {
                        _loginResult.value = ResultViewModel(isLoginSuccessful, err)
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                // Actualizar LiveData en el hilo principal
                withContext(Dispatchers.Main) {
                    _loginResult.value = ResultViewModel(false, ex.message)
                }
            }
        }

    }

    fun validateUser(usuario: String) {
        _userError.value = if (usuario.isBlank()) "Usuario es Obligatorio" else null
    }

    fun validatePassword(password: String) {
        _passwordError.value = if (password.isBlank()) "Contraseña es Obligatorio" else null
    }

}