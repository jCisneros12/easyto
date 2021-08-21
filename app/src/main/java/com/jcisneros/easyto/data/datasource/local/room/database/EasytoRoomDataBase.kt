package com.jcisneros.easyto.data.datasource.local.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jcisneros.easyto.data.datasource.local.room.dao.TaskDao
import com.jcisneros.easyto.data.datasource.local.room.entities.TaskEntity
import com.jcisneros.easyto.utils.Const
import com.jcisneros.easyto.utils.Converters

@Database(entities = [TaskEntity::class], version = Const.EASYTO_DATABASE_VERSION, exportSchema = false)
@TypeConverters(Converters::class)
abstract class EasytoRoomDataBase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        //singleton instance
        @Volatile
        private var INSTANCE: EasytoRoomDataBase? = null

        fun getDataBase(context: Context): EasytoRoomDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EasytoRoomDataBase::class.java,
                    Const.EASYTO_DATABASE_NAME//data base name
                ).build()
                INSTANCE = instance
                //return instance
                return instance
            }
        }
    }

}