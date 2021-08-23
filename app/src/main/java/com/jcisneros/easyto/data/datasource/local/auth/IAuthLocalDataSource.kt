package com.jcisneros.easyto.data.datasource.local.auth

interface IAuthLocalDataSource {
    suspend fun cleanLocalDataBase()
}