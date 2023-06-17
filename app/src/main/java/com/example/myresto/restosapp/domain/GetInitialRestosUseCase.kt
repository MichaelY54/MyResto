package com.example.myresto.restosapp.domain

import com.example.myresto.restosapp.data.RestosRepository
import javax.inject.Inject

class GetInitialRestosUseCase @Inject constructor(
    private val repo: RestosRepository,
    private val getSortedRestoUseCase: GetSortedRestoUseCase
){

    suspend operator fun invoke(): List<Resto>{
        repo.loadRestos()
        return getSortedRestoUseCase()
    }
}