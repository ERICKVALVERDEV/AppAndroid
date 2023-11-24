package com.evalverde.appintegration.ui.loginUser

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.evalverde.appintegration.databinding.ActivityLoginUserBinding
import com.evalverde.appintegration.globalModel.ResultViewModel
import com.evalverde.appintegration.ui.loginUser.model.LoginUserViewModel

class LoginUser : AppCompatActivity() {
    private lateinit var binding: ActivityLoginUserBinding
    private lateinit var loginUserViewModel: LoginUserViewModel
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginUserViewModel = ViewModelProvider(this).get(
            LoginUserViewModel::class.java
        )
        loginUserViewModel.loginResult.observe(this, Observer {
            val loginResult = it ?: return@Observer

            if (loginResult.isValid == true) {
                Toast.makeText(this,"Usuario logueado.",Toast.LENGTH_SHORT).show()
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
            val usuario = binding.edtUsuario.text.toString()
            val clave = binding.edtClave.text.toString()
            loginUserViewModel.performLogin(usuario, clave)
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