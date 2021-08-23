package com.jcisneros.easyto.domain.repository.tasks

import android.util.Log
import com.jcisneros.easyto.data.datasource.firebase.tasks.TasksFirebaseDataSource
import com.jcisneros.easyto.data.datasource.interfaces.ITasksDataSource
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.data.model.modelTaskToEntity
import com.jcisneros.easyto.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect

class TasksRepoImpl(
    private val localDataSource: ITasksDataSource,
    private val firebaseDataSource: TasksFirebaseDataSource
) : ITasksRepo {

    /*
    * this method get list of task from local DB if that, else if empty, then will get data from
    * firestore and will save in local DB
    * */
    @ExperimentalCoroutinesApi
    override suspend fun getAllTasks(): Flow<Resource<List<TaskModel>>> =
        callbackFlow {
            localDataSource.getAllTasks().collect {
                when (it) {
                    is Resource.Success -> {
                        //verify if local data is not empty
                        if (it.data.isNotEmpty()) {
                            Log.i("DATA-DB", "FROM LOCAL DB")
                            //return data from local db
                            trySend(it)
                        } else {
                            //else get data from firebase and save that in local
                            val firebaseData = firebaseDataSource.getTasks()
                            localDataSource.insertTasks(firebaseData)
                            Log.i("DATA-DB", "FROM FIRESTORE")
                            //return data from firebase
                            trySend(firebaseData)
                        }
                    }
                }
            }
        }

    override suspend fun getTaskComplete(): Flow<Resource<List<TaskModel>>> {
        return localDataSource.getTaskComplete()
    }

    override suspend fun getTaskIncomplete(): Flow<Resource<List<TaskModel>>> {
        return localDataSource.getTaskIncomplete()
    }

    //this method update task is complete or not
    override suspend fun updateTaskComplete(taskId: String, isComplete: Boolean): Resource<Boolean> {
    //1.- update on Firebase
        when(firebaseDataSource.updateTaskComplete(taskId, isComplete)){
            is Resource.Success ->{
                //2.- update in local
                localDataSource.updateTaskComplete(taskId, isComplete)
                return Resource.Success(true)
            }
        }
        return Resource.Success(false)
    }

    //delete all tasks in local db
    override suspend fun deleteAllTask(): Resource<Boolean> {
        localDataSource.deleteAllTask()
        return Resource.Success(true)
    }

    //this method get tasks from local db only
    override suspend fun getLocalTasks(): Flow<Resource<List<TaskModel>>> =
        localDataSource.getAllTasks()
}