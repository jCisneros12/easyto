package com.jcisneros.easyto.data.datasource.local.tasks

import com.jcisneros.easyto.data.datasource.interfaces.ITasksDataSource
import com.jcisneros.easyto.data.datasource.local.room.dao.TaskDao
import com.jcisneros.easyto.data.datasource.local.room.entities.TaskEntity
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.data.model.modelTaskToEntity
import com.jcisneros.easyto.data.model.toTaskModel
import com.jcisneros.easyto.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@ExperimentalCoroutinesApi
class TasksLocalDataSource @Inject constructor(private val taskDao: TaskDao) : ITasksDataSource {

    //method to get tasks from local DB
    override suspend fun getAllTasks(): Flow<Resource<List<TaskModel>>> {
        return callbackFlow {
            trySend(Resource.Loading())
            taskDao.getAllTasks().collect {
                val listTask = toTaskModel(it)
                trySend(Resource.Success(listTask))
            }
            awaitClose { cancel() }
        }
    }

    //method to insert data in local db from remote
    override suspend fun insertTasks(data: Resource<List<TaskModel>>){
        when (data) {
            is Resource.Success -> {
                if (data.data.isNotEmpty()) {
                    data.data.forEach { task ->
                        insertTask(modelTaskToEntity(task))
                    }
                }
            }
        }
    }

    //insert one task in local db
    override suspend fun insertTask(taskEntity: TaskEntity): Resource<Boolean> {
        taskDao.insertTask(taskEntity)
        return Resource.Success(true)
    }


    override suspend fun getTaskComplete(): Flow<Resource<List<TaskModel>>>{
        return callbackFlow {
            trySend(Resource.Loading())
            taskDao.getTaskComplete().collect {
                val listTask = toTaskModel(it)
                trySend(Resource.Success(listTask))
            }
            awaitClose { cancel() }
        }
    }

    override suspend fun getTaskIncomplete(): Flow<Resource<List<TaskModel>>> {
        return callbackFlow {
            trySend(Resource.Loading())
            taskDao.getTaskIncomplete().collect {
                val listTask = toTaskModel(it)
                trySend(Resource.Success(listTask))
            }
            awaitClose { cancel() }
        }
    }

    override suspend fun updateTaskComplete(taskId: String, isComplete: Boolean) {
        taskDao.updateTaskComplete(taskId, isComplete)
    }

    override suspend fun deleteAllTask() {
        taskDao.deleteAllTask()
    }
}