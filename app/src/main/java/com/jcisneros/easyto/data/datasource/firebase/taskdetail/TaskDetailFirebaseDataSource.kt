package com.jcisneros.easyto.data.datasource.firebase.taskdetail

import android.content.Context
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.utils.Resource
import kotlinx.coroutines.tasks.await

class TaskDetailFirebaseDataSource() {

    // Access a Cloud Firestore instance
    private val db = Firebase.firestore
    //url image task
    private var imageUrl: String = ""
    //result data from firebase
    private var resultData: DocumentReference? = null


    //return url image task
    suspend fun setNewTask(task: TaskModel): Resource<String>{
        val docReference =  db.collection("users").document("0yZhV7yRuzKUiJplmZ2V")
            .collection("tasks")

        //1.- Upload task image if not null
        if(task.imageUri!=null) imageUrl = ImageFirebaseDataSource().uploadImage(task.imageUri)

        //2.- set new task to Firestore
        if(imageUrl.isNotEmpty()) {
            //create map task
            val taskMap = hashMapOf(
                "title" to task.title,
                "description" to task.description,
                "image" to imageUrl,
                "isComplete" to task.isComplete)

            //try set data to Firestore
            resultData = docReference.add(taskMap).await()

        }else{
            //create map task without image
            val taskMap = hashMapOf(
                "title" to task.title,
                "description" to task.description,
                "image" to "",
                "isComplete" to task.isComplete)

            //try set data to Firestore
            resultData = docReference.add(taskMap).await()

        }

        //return true if successful
        return if (resultData!=null)
            Resource.Success(resultData!!.id)
        else
            Resource.Failure(Exception("Error to set New Task in Firebase"))
    }

}