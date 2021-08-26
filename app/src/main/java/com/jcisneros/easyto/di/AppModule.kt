package com.jcisneros.easyto.di

import android.content.Context
import androidx.room.Room
import com.jcisneros.easyto.data.datasource.local.room.database.EasytoRoomDataBase
import com.jcisneros.easyto.utils.Const
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)//inject for application
object AppModule {

    @Singleton
    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        EasytoRoomDataBase::class.java,
        Const.EASYTO_DATABASE_NAME
    ).build()


    @Singleton
    @Provides
    fun provideTaskDao(dataBase: EasytoRoomDataBase) = dataBase.taskDao()

}