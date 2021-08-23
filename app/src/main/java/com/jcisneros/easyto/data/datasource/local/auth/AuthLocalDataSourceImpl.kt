package com.jcisneros.easyto.data.datasource.local.auth

import com.jcisneros.easyto.data.datasource.local.room.dao.TaskDao

class AuthLocalDataSourceImpl(private val taskDao: TaskDao): IAuthLocalDataSource {
    override suspend fun cleanLocalDataBase() {
        taskDao.deleteAllTask()
    }
}