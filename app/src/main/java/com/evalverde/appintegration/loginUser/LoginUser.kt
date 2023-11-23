package com.evalverde.appintegration.loginUser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.evalverde.appintegration.databinding.ActivityLoginUserBinding
import com.evalverde.appintegration.loginUser.model.LoginUserViewModel

class LoginUser : AppCompatActivity() {
    private lateinit var binding: ActivityLoginUserBinding
    private lateinit var loginUserViewModel : LoginUserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginUserViewModel = ViewModelProvider(this).get(
            LoginUserViewModel::class.java)
        loginUserViewModel.loginResult.observe(this, Observer {
            val loginResult = it ?: return@Observer

            if(loginResult.isValid == true){
                binding.usernameLayout.error = null
                binding.passwordLayout.error = null
            }else{
                binding.usernameLayout.error = loginResult.errorText
                binding.passwordLayout.error = loginResult.errorText
            }
        })

        loginUserViewModel.userError.observe(this, Observer { error -> binding.usernameLayout.error = error })
        loginUserViewModel.passwordError.observe(this, Observer { error -> binding.passwordLayout.error = error })

        binding.edtUsuario.addTextChangedListener { t -> loginUserViewModel.validateUser(t.toString()) }
        binding.edtClave.addTextChangedListener { t -> loginUserViewModel.validatePassword(t.toString()) }

        binding.loginButton.setOnClickListener{
            val usuario = binding.edtUsuario.text.toString()
            val clave = binding.edtClave.text.toString()
            loginUserViewModel.performLogin(usuario,clave)
        }
    }
}