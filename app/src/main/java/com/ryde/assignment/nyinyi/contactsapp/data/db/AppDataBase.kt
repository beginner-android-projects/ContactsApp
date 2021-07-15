package com.ryde.assignment.nyinyi.contactsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ryde.assignment.nyinyi.contactsapp.data.dao.ContactsDao
import com.ryde.assignment.nyinyi.contactsapp.data.dao.RemoteKeysDao
import com.ryde.assignment.nyinyi.contactsapp.data.entity.Contact
import com.ryde.assignment.nyinyi.contactsapp.data.entity.RemoteKeys

@Database(
    entities = [Contact::class, RemoteKeys::class],
    version = 1, exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract val contactsDao: ContactsDao
    abstract val remoteKeysDao: RemoteKeysDao

    //Room should only be initiated once, marked volatile to be thread safe.
    companion object {
        @Volatile
        private var instance: AppDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance ?: buildDatabase(
                    context
                ).also {
                    instance = it
                }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "app_db"
            ).fallbackToDestructiveMigration()
                .build()
    }
}