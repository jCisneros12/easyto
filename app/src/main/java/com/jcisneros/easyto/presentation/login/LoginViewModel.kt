package com.jcisneros.easyto.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.jcisneros.easyto.domain.repository.auth.IAuthRepo
import com.jcisneros.easyto.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: IAuthRepo) : ViewModel() {

    fun loginWithEmailPassword(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repository.loginEmailPassword(email, password))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
            Log.e("AUTH-ERROR", e.toString())
        }
    }

    fun registerEmailPassword(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repository.registerEmailPassword(email, password))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
            Log.e("REGISTER-ERROR", e.toString())
        }
    }

    fun cleanLocalDatabase(){
        viewModelScope.launch {
            repository.cleanLocalDataBase()
        }
    }

}