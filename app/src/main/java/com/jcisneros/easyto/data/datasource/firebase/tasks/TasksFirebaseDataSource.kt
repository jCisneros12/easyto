package com.jcisneros.easyto.data.datasource.firebase.tasks

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jcisneros.easyto.EasytoApplication
import com.jcisneros.easyto.data.datasource.interfaces.ITasksDataSource
import com.jcisneros.easyto.data.datasource.local.room.entities.TaskEntity
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.utils.Resource
import com.jcisneros.tbradminapp.utils.ImageManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.coroutineContext

class TasksFirebaseDataSource(private val context: Context) {

    // Access a Cloud Firestore instance
    private val db = Firebase.firestore

    suspend fun getTasks(): Resource<List<TaskModel>> {
        //list of task
        val arrayTask = mutableListOf<TaskModel>()
        var bitmapImage: Bitmap? = null

        //document reference //TODO: change document for userId logged
        val resultData = db.collection("users")
            .document("0yZhV7yRuzKUiJplmZ2V").collection("tasks").get().await()

        //fetch firebase data
        resultData.forEach { document ->
            //convert image url to Bitmap
            if(document.getString("image").toString().isNotEmpty()){
                bitmapImage = ImageManager.bitmapToUrl(document.getString("image").toString(), context)
            }

            arrayTask.add(
                TaskModel(
                    taskId = document.id,
                    title = document.getString("title").toString(),
                    description = document.getString("description").toString(),
                    isComplete = document.get("isComplete").toString().toBoolean(),
                    image = bitmapImage
                )
            )
        }

        return Resource.Success(arrayTask)
    }


}