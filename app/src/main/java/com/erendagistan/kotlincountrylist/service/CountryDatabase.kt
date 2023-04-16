package com.erendagistan.kotlincountrylist.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.erendagistan.kotlincountrylist.model.Country

@Database(entities = arrayOf(Country::class), version = 1)
abstract class CountryDatabase : RoomDatabase(){

    abstract fun countryDao() : CountryDao

    //Singleton
    //bir sınıftan sadece bir tane obje olusturulabiliyor

    companion object {

        @Volatile private var instance : CountryDatabase?=null

        private val lock = Any()

        operator fun invoke(context: Context) = instance?: synchronized(lock){  //birden fazla thread instance'a ulaşmaya çalışırsa blockluyor!
            instance ?: makeDatabase(context).also {
                instance=it
            }
        }

        private fun makeDatabase(context:Context) = Room.databaseBuilder(
            context.applicationContext,CountryDatabase::class.java,"countrydatabase"
        ).build()
    }

}