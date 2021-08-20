package com.jcisneros.easyto.data.datasource.local.tasks

import com.jcisneros.easyto.data.datasource.interfaces.ITasksDataSource
import com.jcisneros.easyto.data.datasource.local.room.dao.TaskDao
import com.jcisneros.easyto.data.datasource.local.room.entities.TaskEntity
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.data.model.toTaskModel
import com.jcisneros.easyto.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect

class TasksLocalDataSource(private val taskDao: TaskDao) : ITasksDataSource {

    @ExperimentalCoroutinesApi
    override suspend fun getTasks(): Flow<Resource<List<TaskModel>>> {
        return callbackFlow {
            offer(Resource.Loading())
            taskDao.getAllTasks().collect {
                val listTask = toTaskModel(it)
                offer(Resource.Success(listTask))
            }
            awaitClose { cancel() }
        }
    }

    override suspend fun insertTask(taskEntity: TaskEntity): Resource<Boolean> {
        taskDao.insertTask(taskEntity)
        return Resource.Success(true)
    }

    //TODO: delete this

    override fun getAllTasks(): Resource<List<TaskModel>> {

        val listTask = listOf(
            TaskModel(
                "s7f8y2dasfs",
                "Crear logo de la aplicacion", "Crear el logo de la aplicacion" +
                        "segun los requerimientos del cliente", false
            ),
            TaskModel(
                "s7f8ys0452dfs",
                "Terminar logica de login", "crear la capa de data de la aplicacion" +
                        "tal para autenticar el usuario", false
            ),
            TaskModel(
                "s7f8y2as56fs",
                "Crear diagrama de la BD", "terminar diagrama entidad-relacion" +
                        "de la base de datos del sistema X", true
            )
        )

        return Resource.Success(listTask)
    }
}