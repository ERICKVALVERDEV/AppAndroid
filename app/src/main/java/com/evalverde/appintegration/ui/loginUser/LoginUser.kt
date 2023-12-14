package com.evalverde.appintegration.ui.loginUser

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.evalverde.appintegration.components.DisplayAlert
import com.evalverde.appintegration.components.IsConnectivityNetwork
import com.evalverde.appintegration.components.LoadingDialog
import com.evalverde.appintegration.components.SharedPreferencesManager
import com.evalverde.appintegration.databinding.ActivityLoginUserBinding
import com.evalverde.appintegration.onlineClient.model.GenUsuario
import com.evalverde.appintegration.ui.loginUser.model.LoginUserViewModel
import com.evalverde.appintegration.ui.menu.Menu
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginUser : AppCompatActivity() {
    private lateinit var binding: ActivityLoginUserBinding
    val loadingDialog = LoadingDialog(this)
    private val loginUserViewModel: LoginUserViewModel by viewModels()
    val userShared ="user"
    val passwordShared ="pass"
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Verificamos si el usuario ya fue Logeado
//        val loginMap = SharedPreferencesManager(this).obtenerLoginSingle()
//        val usuario = binding.edtUsuario.text.toString()
//        val clave = binding.edtClave.text.toString()
//        if(loginMap.any() && loginMap[0][userShared] != null && loginMap[0][passwordShared] != null){
//            var i = Intent(this, Menu::class.java)
//            startActivity(i)
//            finish()
//        }

        loginUserViewModel.loginResult.observe(this, Observer {
            try {

                val loginResult = it ?: return@Observer

                if (loginResult.isValid) {
                    Toast.makeText(this,"Usuario logueado.",Toast.LENGTH_SHORT).show()
                    val response = loginResult.responseObject as GenUsuario
                    val clave = binding.edtClave.text.toString()
                    var credential = listOf(mapOf(userShared to response.CodigoUsuario, passwordShared to clave))
                    //Guardamos el login en las preferencias
                    SharedPreferencesManager(this).saveLogin(credential)

                    var i = Intent(this, Menu::class.java)
                    startActivity(i)
                    finish()
                } else {
                    loginResult.errorText?.let { it1 -> DisplayAlert(this,"Error", it1).show() }
                }
            }catch (ex: Exception){DisplayAlert(this,"Error", ex.message!!).show()
            }finally {
                loadingDialog.dimissDialog()
            }
        })

        loginUserViewModel.userError.observe(
            this,
            Observer { error -> binding.usernameLayout.error = error })
        loginUserViewModel.passwordError.observe(
            this,
            Observer { error -> binding.passwordLayout.error = error })

        binding.edtUsuario.addTextChangedListener { t -> loginUserViewModel.validateUser(t.toString()) }
        binding.edtClave.addTextChangedListener { t -> loginUserViewModel.validatePassword(t.toString()) }

        binding.loginButton.setOnClickListener {
            val usuario = binding.edtUsuario.text.toString()
            val clave = binding.edtClave.text.toString()
            if(IsConnectivityNetwork(this)){
                loadingDialog.startLoadingDialog()
                loginUserViewModel.performLoginOnline(usuario, clave)
            }else{
                DisplayAlert(this,"Mensaje","Necesita una conexión estable a red.").show()
                //Este codigo funcion cuando se implemento el offline

//                loadingDialog.startLoadingDialog()
//                val loginMap = SharedPreferencesManager(this).obtenerLoginSingle()
//                if(loginMap.any()){
//                    if(loginMap[0][userShared] == usuario && loginMap[0][passwordShared] == clave){
//                        Toast.makeText(this,"Bienvenido...",Toast.LENGTH_SHORT).show()
//                        val i = Intent(this, Menu::class.java)
//                        startActivity(i)
//                        finish()
//                    }else{
//                        DisplayAlert(this,"Mensaje","Credenciales incorrectas.").show()
//                    }
//                }else{
//                    DisplayAlert(this,"Mensaje","No existen credenciales almacenados localmente.").show()
//                }
//                loadingDialog.dimissDialog()
            }
        }

        binding.ContenidoContrain.setOnTouchListener { _, _ ->
            hideKeyboard()
            return@setOnTouchListener true
        }
    }

    fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}