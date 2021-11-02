package com.example.room_tutorial.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// This class is just a one big boiler plate, use this a reference for creating databases in the future

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract  class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao


    // Always use the same instance of room database
    companion object{
        @Volatile
        private var INSTANCE: UserDatabase? = null
        
        fun getDatabase(context: Context) : UserDatabase{
            val  tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"

                ).build()
                INSTANCE = instance
                return instance
            }
            
        }
    }
}