package com.jcisneros.easyto.data.datasource.firebase.taskdetail

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.utils.Resource
import kotlinx.coroutines.tasks.await

class TaskDetailFirebaseDataSource {

    //document reference
    private val docReference = Firebase.firestore.collection("users")
        .document("0yZhV7yRuzKUiJplmZ2V").collection("tasks")
    //url image task
    private var imageUrl: String = ""
    //for get id and url image of task
    private var taskData = arrayListOf<String>()
    //task map
    lateinit var taskMap: Map<String, Any?>

    //return url image and id
    // TODO: change data from user auth
    suspend fun setNewTask(task: TaskModel): Resource<List<String>> {
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

    //method for delete task by id
    suspend fun deleteTaskById(taskId: String): Resource<Boolean> {
        val resultData = docReference.document(taskId).delete().await()
        //return true if success
        return if(resultData!=null) Resource.Success(false)
        else Resource.Success(true)
    }

    //method for update task by id
    suspend fun updateTaskById(taskId: String, taskModel: TaskModel): Resource<List<String>>{

        //update image if user select a new image
        if(taskModel.imageUri!=null) {
            imageUrl = ImageFirebaseDataSource().uploadImage(taskModel.imageUri)
            taskMap = hashMapOf(
                "title" to taskModel.title,
                "description" to taskModel.description,
                "image" to imageUrl,
                "isComplete" to taskModel.isComplete
            )
        }else{
            taskMap = hashMapOf(
                "title" to taskModel.title,
                "description" to taskModel.description,
                "image" to taskModel.image,
                "isComplete" to taskModel.isComplete
            )
        }
        //update in Firestore
        val resultData = docReference.document(taskId).update(taskMap).await()

        //return taskData if successful
        return if (resultData != null) {
            Resource.Failure(Exception("Error to Update Task in Firebase"))
        } else {
            if(imageUrl.isNotEmpty()) taskData.add(imageUrl)
            else taskData.add(taskModel.image!!)
            Resource.Success(taskData)
        }
    }


}