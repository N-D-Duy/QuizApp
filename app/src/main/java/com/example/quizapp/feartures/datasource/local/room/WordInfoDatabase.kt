package com.example.quizapp.feartures.datasource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.quizapp.feartures.datasource.local.room.entity.WordInfoEntity
import com.example.quizapp.feartures.datasource.local.room.entity.dao.WordInfoDao

@Database(
    entities = [WordInfoEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class WordInfoDatabase: RoomDatabase() {
    abstract val wordDao: WordInfoDao
}