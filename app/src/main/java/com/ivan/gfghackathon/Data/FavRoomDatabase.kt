package com.ivan.gfghackathon.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ivan.gfghackathon.Model.FavDao
import com.ivan.gfghackathon.Model.FavRecipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(FavRecipe::class), version = 1, exportSchema = false)
abstract class FavRoomDatabase :RoomDatabase() {

    abstract fun favDao():FavDao

    private class sRoomDatabaseCallback(private val scope:CoroutineScope): RoomDatabase.Callback(){

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let{
                scope.launch {
                    it.favDao().onDeleteAllDao()
                }
            }
        }
    }

    companion object{

        @Volatile
        private var INSTANCE:FavRoomDatabase? = null

        fun getDatabaseInstance(context: Context,scope:CoroutineScope):FavRoomDatabase{

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context,FavRoomDatabase::class.java,"FavRecipe")
                    .addCallback(sRoomDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}