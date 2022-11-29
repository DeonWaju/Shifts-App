package com.example.shiftstestapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shiftstestapplication.data.db.entities.ShiftItems

@Database(
    entities = [ShiftItems::class],
    version = 1
)
abstract class ShiftsDatabase : RoomDatabase() {

    abstract fun shiftsDao(): ShiftsDao

    companion object {
        @Volatile
        private var dbInstance: ShiftsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = dbInstance ?: synchronized(LOCK) {
            dbInstance ?: createDatabase(context).also { dbInstance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ShiftsDatabase::class.java, "ShiftsDatabase.db"
        ).build()
    }
}