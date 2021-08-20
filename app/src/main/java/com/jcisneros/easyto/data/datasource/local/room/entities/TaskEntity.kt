package com.jcisneros.easyto.data.datasource.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
class TaskEntity(
    //@PrimaryKey(autoGenerate = true) val id: Int,

    @PrimaryKey
    @ColumnInfo(name = "task_id")
    val taskId: String,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "is_complete")
    val isComplete: Boolean,

    @ColumnInfo(name = "image_task")
    val image: String? = null
)
