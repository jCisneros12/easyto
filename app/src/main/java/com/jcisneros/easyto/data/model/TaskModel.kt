package com.jcisneros.easyto.data.model

import android.graphics.Bitmap
import android.net.Uri
import com.jcisneros.easyto.data.datasource.local.room.entities.TaskEntity

data class TaskModel(
    var taskId: String,
    val title: String?,
    val description: String?,
    val isComplete: Boolean,
    var image: String? = null,
    val imageUri: Uri? = null
)

//convert entity to model
fun toTaskModel(entityList: List<TaskEntity>): List<TaskModel> {
    val taskModelList  = mutableListOf<TaskModel>()
    entityList.forEach { entity ->
            val taskModel = TaskModel(
                taskId =entity.taskId,
                title = entity.title!!,
                description = entity.description!!,
                isComplete = entity.isComplete,
                image = entity.image
            )
        taskModelList.add(taskModel)
    }
    return taskModelList
}

//convert model to entity
fun toTaskEntity(modelList: List<TaskModel>): List<TaskEntity> {
    val taskEntityList  = mutableListOf<TaskEntity>()
    modelList.forEach { model ->
        val taskEntity = TaskEntity(
            taskId = model.taskId,
            title = model.title,
            description = model.description,
            isComplete = model.isComplete,
            image = model.image
        )
        taskEntityList.add(taskEntity)
    }
    return taskEntityList
}

//convert TaskModel to TaskEntity

fun modelTaskToEntity(taskModel: TaskModel): TaskEntity{
    return TaskEntity(
        taskId = taskModel.taskId,
        title = taskModel.title,
        description = taskModel.description,
        isComplete = taskModel.isComplete,
        image = taskModel.image
    )
}

//convert TaskEntity to TaskModel

fun entityTaskToModel(tskEntity: TaskEntity): TaskModel{
    return TaskModel(
        taskId = tskEntity.taskId,
        title = tskEntity.title,
        description = tskEntity.description,
        isComplete = tskEntity.isComplete,
        image = tskEntity.image
    )
}