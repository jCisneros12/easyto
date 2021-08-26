package com.jcisneros.easyto.data.datasource.local.auth

import com.jcisneros.easyto.data.datasource.local.room.dao.TaskDao
import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor(private val taskDao: TaskDao): IAuthLocalDataSource {
    override suspend fun cleanLocalDataBase() {
        taskDao.deleteAllTask()
    }
}