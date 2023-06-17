package com.example.myresto.restosapp.domain

import com.example.myresto.restosapp.data.RestosRepository
import javax.inject.Inject

class GetSortedRestoUseCase @Inject constructor(
    private val repo: RestosRepository
){
//    private val repo: RestosRepository = RestosRepository()
    suspend operator fun invoke(): List<Resto>{
        return repo.getRestos().sortedBy { it.title }
    }
}