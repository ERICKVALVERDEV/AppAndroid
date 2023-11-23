package com.evalverde.appintegration.loginApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.evalverde.appintegration.dataAccess.Repository.UsuarioLocalRepository
import com.evalverde.appintegration.databinding.ActivityLoginappBinding
import com.evalverde.appintegration.loginApp.model.LoginAppViewModel
import com.evalverde.appintegration.loginApp.model.LoginAppViewModelFactory

class LoginAppActivity : AppCompatActivity(){

    private lateinit var loginAppViewModel: LoginAppViewModel
    private lateinit var binding: ActivityLoginappBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginappBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userCredentialRepository = UsuarioLocalRepository(null)
        val viewModelFactory = LoginAppViewModelFactory(userCredentialRepository)
        loginAppViewModel = ViewModelProvider(this, LoginAppViewModelFactory(userCredentialRepository)).get(LoginAppViewModel::class.java)
        loginAppViewModel.loginResult.observe(this@LoginAppActivity, Observer {
            val loginResult = it ?: return@Observer

            if(loginResult.isValid == true){
                binding.usernameLayout.error = null
                binding.passwordLayout.error = null
            }else{
                binding.usernameLayout.error = loginResult.errorText
                binding.passwordLayout.error = loginResult.errorText
            }
        })

        loginAppViewModel.userError.observe(this, Observer { error -> binding.usernameLayout.error = error })
        loginAppViewModel.passwordError.observe(this, Observer { error -> binding.passwordLayout.error = error })

        binding.edtUsuario.addTextChangedListener { t -> loginAppViewModel.validateUser(t.toString()) }
        binding.edtClave.addTextChangedListener { t -> loginAppViewModel.validatePassword(t.toString()) }

        binding.loginButton.setOnClickListener{
            val usuario = binding.edtUsuario.text.toString()
            val clave = binding.edtClave.text.toString()
            loginAppViewModel.performLogin(usuario,clave)
        }
    }


}