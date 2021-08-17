package com.jcisneros.easyto.data.datasource.local.tasks

import com.jcisneros.easyto.data.datasource.interfaces.ITasksDataSource
import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.utils.Resource

class TasksLocalDataSource: ITasksDataSource{

    override fun getAllTasks(): Resource<List<TaskModel>>{

        val listTask = listOf(
            TaskModel("Crear logo de la aplicacion", "Crear el logo de la aplicacion" +
                    "segun los requerimientos del cliente", false) ,
            TaskModel("Terminar logica de login", "crear la capa de data de la aplicacion" +
                    "tal para autenticar el usuario", false),
            TaskModel("Crear diagrama de la BD", "terminar diagrama entidad-relacion" +
                    "de la base de datos del sistema X", true)
        )

        return Resource.Success(listTask)
    }
}