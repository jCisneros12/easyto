package com.jcisneros.easyto.data.datasource.local.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jcisneros.easyto.data.datasource.local.room.dao.TaskDao
import com.jcisneros.easyto.data.datasource.local.room.entities.TaskEntity
import com.jcisneros.easyto.utils.Const

@Database(entities = [TaskEntity::class], version = Const.EASYTO_DATABASE_VERSION, exportSchema = false)
abstract class EasytoRoomDataBase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

}