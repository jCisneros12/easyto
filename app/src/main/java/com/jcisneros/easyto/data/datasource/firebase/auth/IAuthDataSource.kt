package com.jcisneros.easyto.data.datasource.firebase.auth

import com.jcisneros.easyto.data.model.UserModel
import com.jcisneros.easyto.utils.Resource

interface IAuthDataSource {
    suspend fun loginEmailPassword(email: String, password: String): Resource<UserModel>
    suspend fun registerEmailPassword(email: String, password: String): Resource<UserModel>
    suspend fun createUserFirestore(userModel: UserModel): Resource<Boolean>
}