package com.jcisneros.easyto.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.jcisneros.easyto.EasytoApplication
import com.jcisneros.easyto.R
import com.jcisneros.easyto.data.datasource.firebase.auth.AuthFirebaseDataSource
import com.jcisneros.easyto.data.datasource.local.auth.AuthLocalDataSourceImpl
import com.jcisneros.easyto.data.datasource.local.room.database.EasytoRoomDataBase
import com.jcisneros.easyto.databinding.ActivityLoginBinding
import com.jcisneros.easyto.domain.repository.auth.AuthRepoImpl
import com.jcisneros.easyto.presentation.MainActivity
import com.jcisneros.easyto.utils.Resource

class LoginActivity : AppCompatActivity() {

    //View Binding
    private lateinit var binding: ActivityLoginBinding

    //ViewModel
    private val viewModel by lazy {
        LoginViewModel(
            AuthRepoImpl(AuthFirebaseDataSource(), AuthLocalDataSourceImpl(
                EasytoRoomDataBase.getDataBase(this.applicationContext).taskDao()
            ))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            if(validateForm()){
                val email = binding.inputTxtEmailLogin.text.toString().trim()
                val password = binding.inputTxtPasswordLogin.text.toString().trim()
                loginWithEmailPassword(email, password)
            }
        }

        binding.buttonRegister.setOnClickListener {
            if(validateForm()){
                val email = binding.inputTxtEmailLogin.text.toString().trim()
                val password = binding.inputTxtPasswordLogin.text.toString().trim()
                registerEmailPassword(email, password)
            }
        }

    }

    private fun validateForm(): Boolean {
        if(TextUtils.isEmpty(binding.inputTxtEmailLogin.text.toString())){
            binding.inputTxtEmailLogin.error = this.getString(R.string.required_email)
            return false
        }
        if(TextUtils.isEmpty(binding.inputTxtPasswordLogin.text.toString())){
            binding.inputTxtPasswordLogin.error = this.getString(R.string.required_password)
            return false
        }
        return true
    }

    //login user with email and password
    private fun loginWithEmailPassword(email: String, password: String){
        //clean local db
        viewModel.cleanLocalDatabase()
        //try login user
        viewModel.loginWithEmailPassword(email, password).observe(this, {
            when(it){
                is Resource.Loading ->{
                    binding.loginProgressBar.visibility = View.VISIBLE
                }
                is Resource.Success ->{
                    binding.loginProgressBar.visibility = View.GONE
                    startApplication()
                }
                is Resource.Failure ->{
                    binding.loginProgressBar.visibility = View.GONE
                    Toast.makeText(this, getString(R.string.login_fail), Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    //register user with email and password
    private fun registerEmailPassword(email: String, password: String){
        viewModel.registerEmailPassword(email, password).observe(this, {
            when(it){
                is Resource.Loading ->{
                    binding.loginProgressBar.visibility = View.VISIBLE
                }
                is Resource.Success ->{
                    binding.loginProgressBar.visibility = View.GONE
                    Toast.makeText(this, getString(R.string.register_success),
                        Toast.LENGTH_LONG).show()
                }
                is Resource.Failure ->{
                    binding.loginProgressBar.visibility = View.GONE
                    Toast.makeText(this, getString(R.string.error_msg), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    //go to main activity
    private fun startApplication(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    //check if any user login
    override fun onStart() {
        super.onStart()
        val userId = EasytoApplication.authPrefs.getUserId(EasytoApplication.prefsInstance)
        if(userId.isNotEmpty()){
            startApplication()
        }
    }
}