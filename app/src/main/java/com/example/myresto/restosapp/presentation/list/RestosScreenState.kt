package com.example.myresto.restosapp.presentation.list

import com.example.myresto.restosapp.domain.Resto

data class RestosScreenState (
    val restos: List<Resto>,
    val isLoading: Boolean,
    val error: String? = null
)