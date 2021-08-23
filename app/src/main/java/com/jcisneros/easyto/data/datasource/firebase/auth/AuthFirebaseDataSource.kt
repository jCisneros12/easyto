package com.jcisneros.easyto.data.datasource.firebase.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jcisneros.easyto.data.model.UserModel
import com.jcisneros.easyto.utils.Resource
import kotlinx.coroutines.tasks.await


class AuthFirebaseDataSource : IAuthDataSource {

    private lateinit var auth: FirebaseAuth

    override suspend fun loginEmailPassword(email: String, password: String): Resource<UserModel> {
        // Initialize Firebase Auth
        auth = Firebase.auth

        //try to login user
        return try {
            val userAuth = auth.signInWithEmailAndPassword(email, password).await()
            val userUid = userAuth.user?.uid
            val userEmail = userAuth.user?.email
            val user = UserModel(userUid, userEmail)
            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Failure(e)
        }

    }

    override suspend fun registerEmailPassword(email: String, password: String): Resource<UserModel> {
        // Initialize Firebase Auth
        auth = Firebase.auth

        //try to register user
        return try {
            val userRegister = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = userRegister.user?.uid
            val userEmail = userRegister.user?.email
            val user = UserModel(userId, userEmail)
            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun createUserFirestore(userModel: UserModel): Resource<Boolean> {
        val docRef = Firebase.firestore.collection("users")
        val userFirestore = docRef.document(userModel.userId!!)
            .set(hashMapOf("email" to userModel.email)).await()

        //if user create success
        if(userFirestore!=null){
            Resource.Failure(Exception("Error to create user data"))
        }
        else{
            Resource.Success(true)
        }
        return Resource.Failure(Exception("Error to create user"))
    }
}