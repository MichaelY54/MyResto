package com.example.myresto.restosapp.domain

import com.example.myresto.restosapp.data.RestosRepository
import javax.inject.Inject

class ToggleRestoUseCase @Inject constructor(
    private val repo: RestosRepository,
    private val getSortedRestoUseCase: GetSortedRestoUseCase
){
//    private val repo: RestosRepository = RestosRepository()
//    private val getSortedRestosUseCase = GetSortedRestoUseCase()
    suspend operator fun invoke(id: Int, oldValue: Boolean): List<Resto>{

        val newFav = oldValue.not()
        repo.toggleFavoriteRoom(id, newFav)
        return getSortedRestoUseCase()
    }
}