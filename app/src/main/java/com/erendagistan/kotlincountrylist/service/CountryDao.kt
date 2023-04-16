package com.erendagistan.kotlincountrylist.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.erendagistan.kotlincountrylist.model.Country
@Dao
interface CountryDao {
    //data access object

    @Insert
    suspend fun insertAll(vararg countries:Country) : List<Long>
    //Insert -> INSERT INTO
    //suspend -> coroutine, pause & resume
    //vararg -> multiple country object
    //List<Long> -> primary keys

    @Query("SELECT * FROM Country")
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * FROM Country WHERE uuid = :countryId")
    suspend fun getCountry(countryId :Int) : Country

    @Query("DELETE FROM Country")
    suspend fun deleteAllCountries()
}