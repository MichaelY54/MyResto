package com.example.myresto.restosapp.domain

data class Resto (
    val id: Int,
    val title: String,
    val description: String,
    val isFavorite: Boolean = false
)