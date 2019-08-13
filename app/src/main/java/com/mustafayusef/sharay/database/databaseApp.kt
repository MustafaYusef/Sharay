package com.mustafayusef.sharay.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.mustafayusef.sharay.database.entitis.latestCar

@Database(entities = [latestCar::class], version = 2)
public abstract class databaseApp : RoomDatabase() {


    abstract fun Car_Dao(): Car_Dao


    companion object {
        @Volatile
        private var INSTANCE: databaseApp? = null
        private val Lock=Any()

        operator fun invoke(context: Context)=INSTANCE?: synchronized(Lock){
            INSTANCE?:getDatabase(context).also {
                INSTANCE=it
            }
        }

        fun getDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                databaseApp::class.java,
                "sharay"
            ).fallbackToDestructiveMigration().build()

    }
}



