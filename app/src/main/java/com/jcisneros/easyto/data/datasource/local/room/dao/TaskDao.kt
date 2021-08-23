package com.jcisneros.easyto.data.datasource.local.room.dao

import androidx.room.*
import com.jcisneros.easyto.data.datasource.local.room.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_table")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task_table WHERE is_complete=:isComplete")
    fun getTaskComplete(isComplete: Boolean = true): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task_table WHERE is_complete=:isComplete")
    fun getTaskIncomplete(isComplete: Boolean = false): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(taskEntity: TaskEntity)

    @Query("DELETE FROM task_table")
    suspend fun deleteAllTask()

    @Query("SELECT * FROM task_table WHERE task_id = :taskId ")
    suspend fun getTaskById(taskId: String): TaskEntity

    @Query("DELETE FROM task_table WHERE task_id=:taskId")
    suspend fun deleteTaskById(taskId: String)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTaskById(task: TaskEntity)

    @Query("UPDATE task_table SET is_complete=:isComplete WHERE task_id=:taskId")
    suspend fun updateTaskComplete(taskId: String, isComplete: Boolean)

    @Query("SELECT * FROM task_table WHERE title LIKE :taskTitle")
    fun getTaskByTitle(taskTitle: String): Flow<List<TaskEntity>>

}