package com.jcisneros.easyto.domain.repository.auth

import com.jcisneros.easyto.EasytoApplication
import com.jcisneros.easyto.data.datasource.firebase.auth.AuthFirebaseDataSource
import com.jcisneros.easyto.data.datasource.firebase.auth.IAuthDataSource
import com.jcisneros.easyto.data.datasource.local.auth.IAuthLocalDataSource
import com.jcisneros.easyto.data.model.UserModel
import com.jcisneros.easyto.utils.Resource
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val datasource: IAuthDataSource,
    private val localDataSource: IAuthLocalDataSource
) : IAuthRepo {
    //if user login success, save token in prefs
    override suspend fun loginEmailPassword(email: String, password: String): Resource<UserModel> {
        //try login user
        val userAuth = datasource.loginEmailPassword(email, password)
        //try save User ID in shared prefs
        when (userAuth) {
            is Resource.Success -> {
                //save id in prefs
                EasytoApplication.authPrefs.saveUserId(
                    userAuth.data.userId.toString(),
                    EasytoApplication.prefsInstance
                )
            }
        }
        return userAuth
    }

    //create user in Firestore if user register success
    override suspend fun registerEmailPassword(
        email: String,
        password: String
    ): Resource<UserModel> {
        //try to register user
        val userRegister = datasource.registerEmailPassword(email, password)
        //try save user id in prefs
        when (userRegister) {
            is Resource.Success -> {
                //Create user in firestore
                when (datasource.createUserFirestore(userRegister.data)) {
                    is Resource.Success -> {
                        //save id in prefs
                        EasytoApplication.authPrefs.saveUserId(
                            userRegister.data.userId.toString(),
                            EasytoApplication.prefsInstance
                        )
                    }
                }
            }
        }
        return userRegister
    }

    override suspend fun cleanLocalDataBase() {
        localDataSource.cleanLocalDataBase()
    }
}