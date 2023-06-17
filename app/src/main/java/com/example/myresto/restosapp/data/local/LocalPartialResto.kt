package com.example.myresto.restosapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
class LocalPartialResto (
    @ColumnInfo(name = "r_id")
    val id: Int,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false
)