package com.jcisneros.easyto.data.datasource.interfaces

import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.utils.Resource

interface ITasksDataSource {
    fun getAllTasks(): Resource<List<TaskModel>>
}