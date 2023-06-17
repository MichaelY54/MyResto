package com.example.myresto.restosapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//GANTI VERSI KALO ADA MASALAH DATA INTEGRITY
@Database(
    entities = [LocalResto::class],
    version = 3,
    exportSchema = false
)

abstract class RestoDb: RoomDatabase(){
    abstract val dao: RestoDao
//    companion object{
//        @Volatile
//        private var INSTANCE: RestoDao? = null
//
//        fun getDaoInstance(c: Context): RestoDao {
//            synchronized(this){
//                var instance = INSTANCE
//                if(instance == null){
//                    instance = buildDatabase(c).dao
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
//
//        private fun buildDatabase(c: Context):
//                RestoDb = Room.databaseBuilder(c.applicationContext,
//            RestoDb::class.java,
//            "restaurants_database").fallbackToDestructiveMigration()
//            .build()
//    }
}