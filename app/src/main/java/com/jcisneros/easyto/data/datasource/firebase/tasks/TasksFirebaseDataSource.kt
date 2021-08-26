package com.jcisneros.easyto.data.datasource.firebase.tasks

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jcisneros.easyto.EasytoApplication
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class TasksFirebaseDataSource @Inject constructor(){
    private val userPath = EasytoApplication.authPrefs.getUserId(EasytoApplication.prefsInstance)
    //document reference
    private val docReference = Firebase.firestore.collection("users")
        .document(userPath).collection("tasks")

    suspend fun getTasks(): Resource<List<TaskModel>> {
        //list of task
        val arrayTask = mutableListOf<TaskModel>()

        //document reference
        val resultData = docReference.get().await()

        //fetch firebase data
        resultData.forEach { document ->
            arrayTask.add(
                TaskModel(
                    taskId = document.id,
                    title = document.getString("title").toString(),
                    description = document.getString("description").toString(),
                    isComplete = document.get("isComplete").toString().toBoolean(),
                    image = document.getString("image").toString()
                )
            )
        }

        return Resource.Success(arrayTask)
    }

    //method for update task (if complete or not)
    suspend fun updateTaskComplete(idTask: String, isComplete: Boolean): Resource<Boolean> {
        //data to update on Firestore
        val taskComplete: Map<String, Any?> = hashMapOf("isComplete" to isComplete)
        //try update task data
        val resultData = docReference.document(idTask).update(taskComplete).await()
        //return true if success
        return if (resultData != null) {
            Resource.Failure(Exception("Error to Update Task in Firebase"))
        } else {
            Resource.Success(true)
        }
    }
}