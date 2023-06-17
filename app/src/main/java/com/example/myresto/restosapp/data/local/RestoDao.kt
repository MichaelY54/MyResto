package com.example.myresto.restosapp.data.local

import androidx.room.*

@Dao
interface RestoDao {
    @Query("SELECT * FROM restaurants")
    suspend fun getAll(): List<LocalResto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(restos: List<LocalResto>)

    @Update(entity = LocalResto::class)
    suspend fun updateFavorite(localPartialResto: LocalPartialResto)

    @Update(entity = LocalResto::class)
    suspend fun updateAll(listLocalPartialResto: List<LocalPartialResto>)

    @Query("SELECT * FROM restaurants WHERE is_favorite = 1")
    suspend fun getAllFavorite(): List<LocalResto>
}