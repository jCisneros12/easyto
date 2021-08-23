package com.jcisneros.easyto.domain.repository.auth

import com.jcisneros.easyto.data.model.UserModel
import com.jcisneros.easyto.utils.Resource

interface IAuthRepo {
    suspend fun loginEmailPassword(email: String, password: String): Resource<UserModel>
    suspend fun registerEmailPassword(email: String, password: String): Resource<UserModel>
    suspend fun cleanLocalDataBase()
}