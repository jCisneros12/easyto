package com.jcisneros.easyto.data.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jcisneros.easyto.data.datasource.local.room.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_table")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(taskEntity: TaskEntity)

    @Query("DELETE FROM task_table")
    suspend fun deleteAllTask()

    @Query("SELECT * FROM task_table WHERE task_id = :taskId ")
    suspend fun getTaskById(taskId: String): TaskEntity
}