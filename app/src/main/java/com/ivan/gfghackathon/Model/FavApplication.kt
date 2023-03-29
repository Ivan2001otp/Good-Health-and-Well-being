package com.ivan.gfghackathon.Model

import android.app.Application
import com.ivan.gfghackathon.Data.FavRoomDatabase
import com.ivan.gfghackathon.Data.RoomRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FavApplication : Application() {
    private val scope:CoroutineScope = CoroutineScope(SupervisorJob())

    val database by lazy{
        FavRoomDatabase.getDatabaseInstance(this,scope)
    }

    val repository by lazy{
        RoomRepository(database.favDao())
    }
}