package com.crystalline.photoalbum.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.crystalline.photoalbum.model.MyImages

@Database(entities = [MyImages::class], version = 1, exportSchema = false)
abstract class MyImagesDatabase : RoomDatabase() {
    abstract fun myImagesDao() : MyImagesDao

    // singleton design pattern
    companion object {

        @Volatile
        private  var instnace : MyImagesDatabase? = null

        fun getDatabaseInstance(context: Context) : MyImagesDatabase {
            synchronized(this) {
                if (instnace == null) {
                    instnace = Room.databaseBuilder(
                        context.applicationContext,
                        MyImagesDatabase::class.java,
                        "my_album"
                    ).build()
                }

                return instnace as MyImagesDatabase
            }
        }
    }
}