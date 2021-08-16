package com.jcisneros.easyto.domain.repository.tasks

import com.jcisneros.easyto.data.model.TaskModel
import com.jcisneros.easyto.utils.Resource

interface ITasksRepo {
    fun getAllTask(): Resource<List<TaskModel>>
}