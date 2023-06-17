package com.example.myresto.restosapp.presentation.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myresto.restosapp.data.remote.RestoranApiService
import com.example.myresto.restosapp.domain.Resto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestoDetailViewModel(private val stateHandle: SavedStateHandle): ViewModel(){
    private var restInterface: RestoranApiService
    val state = mutableStateOf<Resto?>(null)
    init{
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://realtimedb-3ef48-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .build()
        restInterface = retrofit.create(
            RestoranApiService::class.java
        )

        val id = stateHandle.get<Int>("restaurant_id")?:0

        viewModelScope.launch {
            val resto = getResto(id)
            state.value = resto
        }
    }

    private suspend fun getResto(id: Int): Resto {
        return withContext(Dispatchers.IO){
            val response = restInterface.getRestoCoroutine(id)
            return@withContext response.values.first().let {
                Resto(
                    id = it.id, title = it.title, description = it.description
                )
            }
        }
    }
}