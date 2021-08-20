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
    override fun getAllTask(): Resource<List<TaskModel>> {
        return localDataSource.getAllTasks()
    }

    //for update local db
    var isUpdate = true

    //get all task
    override suspend fun getLocalTasks(): Flow<Resource<List<TaskModel>>> =
        localDataSource.getTasks()

    //this method get list of task from local DB if that is not empty, else is empty, then get data
    //from firestore and will save in local DB
    @ExperimentalCoroutinesApi
    override suspend fun getTasks(): Flow<Resource<List<TaskModel>>> =
        callbackFlow {
            localDataSource.getTasks().collect {
                //verify if local data is not empty
                when (it) {
                    is Resource.Success -> {
                        if (it.data.isNotEmpty()) {
                            Log.i("DATA-DB", "FROM LOCAL DB")
                            trySend(it)
                        } else {
                            val firebaseData = firebaseDataSource.getTasks()
                            //TODO: set data to local db
                            when (firebaseData) {
                                is Resource.Success -> {
                                    if (firebaseData.data.isNotEmpty()) {
                                        firebaseData.data.forEach { task ->
                                            localDataSource.insertTask(modelTaskToEntity(task))
                                        }
                                    }
                                }
                            }
                            Log.i("DATA-DB", "FROM FIRESTORE")
                            trySend(firebaseData)
                        }
                    }
                }
            }
            //get data from Firestore and save in local db
//            if (!isUpdate) {
//                firebaseDataSource.getTasks().collect {
//                    when (it) {
//                        is Resource.Success -> {
//                            if (it.data.isNotEmpty()) {
//                                //updale local db
//                                it.data.forEach { task ->
//                                    localDataSource.insertTask(modelTaskToEntity(task))
//                                }
//                            }
//                            offer(it)
//                        }
//                    }
//                }
//            }
        }
}