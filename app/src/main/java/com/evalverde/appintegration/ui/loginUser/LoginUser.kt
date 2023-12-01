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
import com.evalverde.appintegration.components.IsConnectivityNetwork
import com.evalverde.appintegration.databinding.ActivityLoginUserBinding
import com.evalverde.appintegration.ui.loginUser.model.LoginUserViewModel
import com.evalverde.appintegration.ui.menu.Menu
import dagger.hilt.android.AndroidEntryPoint
class LoginUser : AppCompatActivity() {
    private lateinit var binding: ActivityLoginUserBinding
    private val loginUserViewModel: LoginUserViewModel by viewModels()
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        loginUserViewModel = ViewModelProvider(this).get(
//            LoginUserViewModel::class.java
//        )
        loginUserViewModel.loginResult.observe(this, Observer {
            val loginResult = it ?: return@Observer

            if (loginResult.isValid == true) {
                Toast.makeText(this,"Usuario logueado.",Toast.LENGTH_SHORT).show()

                var i = Intent(this, Menu::class.java)
                startActivity(i)
            } else {
                Toast.makeText(this,loginResult.errorText,Toast.LENGTH_SHORT).show()
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
            if(IsConnectivityNetwork(this)){
                val usuario = binding.edtUsuario.text.toString()
                val clave = binding.edtClave.text.toString()
                loginUserViewModel.performLogin(usuario, clave)
            }else{

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