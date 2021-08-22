package com.jcisneros.easyto.domain.repository.taskdetail

import com.jcisneros.easyto.data.datasource.firebase.taskdetail.TaskDetailFirebaseDataSource
import com.jcisneros.easyto.data.datasource.interfaces.ITaskDetailDataSource
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.utils.Resource

class TaskDetailRepoImpl(
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
}