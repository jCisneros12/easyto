package com.jcisneros.easyto.domain.repository.taskdetail

import com.jcisneros.easyto.data.datasource.firebase.taskdetail.TaskDetailFirebaseDataSource
import com.jcisneros.easyto.data.datasource.interfaces.ITaskDetailDataSource
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.utils.Resource
import javax.inject.Inject

class TaskDetailRepoImpl @Inject constructor(
    private val firebaseDataSource: TaskDetailFirebaseDataSource,
    private val localDataSource: ITaskDetailDataSource,
) : ITaskDetailRepo {

    /*
    * this method save data in Firebase and then (if success) save data in local db
    * */
    override suspend fun insertNewTask(taskModel: TaskModel): Resource<Boolean> {
        //1.- set data to firebase
        when (val firebaseRes = firebaseDataSource.setNewTask(taskModel)) {
            is Resource.Success -> {
                //2.- set data to local DB
                taskModel.taskId = firebaseRes.data[0] //change to Firebase id
                taskModel.image = firebaseRes.data[1]//set url image
                localDataSource.insertNewTask(taskModel)
                return Resource.Success(true)
            }
        }
        return Resource.Success(false)
    }

    //get task By id from local db
    override suspend fun getTaskById(taskId: String): Resource<TaskModel> {
        return localDataSource.getTaskById(taskId)
    }

    //delete task
    override suspend fun deleteTaskById(taskId: String): Resource<Boolean> {
        //1.- delete task on Firestore
        when (val deleteFirebase = firebaseDataSource.deleteTaskById(taskId)) {
            is Resource.Success -> {
                if (deleteFirebase.data) {
                    //2.- Delete task in local db
                    localDataSource.deleteTaskById(taskId)
                    return Resource.Success(true)
                }
            }
        }
        return Resource.Success(false)
    }

    //update task
    override suspend fun updateTaskById(taskId: String, taskModel: TaskModel): Resource<Boolean> {
        //1.- update task on firestore
        when (val updateFirestore = firebaseDataSource.updateTaskById(taskId, taskModel)) {
            is Resource.Success -> {
                //2.- update task in local db
                taskModel.image = updateFirestore.data[0] //url image task
                taskModel.taskId = taskId
                localDataSource.updateTaskById(taskModel)
                return Resource.Success(true)
            }
        }
        return Resource.Success(false)
    }
}