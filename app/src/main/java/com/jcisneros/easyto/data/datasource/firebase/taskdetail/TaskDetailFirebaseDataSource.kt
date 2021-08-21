package com.jcisneros.easyto.data.datasource.firebase.taskdetail

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.utils.Resource
import kotlinx.coroutines.tasks.await

class TaskDetailFirebaseDataSource {

    // Access a Cloud Firestore instance
    private val db = Firebase.firestore

    //url image task
    private var imageUrl: String = ""

    //for get id and url image of task
    private var taskData = arrayListOf<String>()

    //return url image and id
    // TODO: change data from user auth
    suspend fun setNewTask(task: TaskModel): Resource<List<String>> {
        val docReference = db.collection("users").document("0yZhV7yRuzKUiJplmZ2V")
            .collection("tasks")

        //1.- Upload task image if not null
        if (task.imageUri != null) imageUrl = ImageFirebaseDataSource().uploadImage(task.imageUri)

        //2.- set new task to Firestore
        //create map task
        val taskMap = hashMapOf(
            "title" to task.title,
            "description" to task.description,
            "image" to imageUrl,
            "isComplete" to task.isComplete
        )

        //try set data to Firestore
        val resultData = docReference.add(taskMap).await()

        //return true if successful
        return if (resultData != null){
            taskData.add(resultData.id)//id task
            taskData.add(imageUrl)//url image task
            Resource.Success(taskData)
        }
        else
            Resource.Failure(Exception("Error to set New Task in Firebase"))
    }

}