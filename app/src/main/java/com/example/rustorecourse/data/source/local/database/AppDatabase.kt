package com.example.rustorecourse.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rustorecourse.data.source.local.entity.AppEntity

@Database(
    entities = [AppEntity::class],
    version = 2,
    exportSchema = true
)
@TypeConverters(CategoryConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDetailsDao(): AppDetailsDao

    companion object {
        const val DATABASE_NAME = "app_database"
    }
}