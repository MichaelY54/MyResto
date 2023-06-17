package com.example.myresto.restosapp.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myresto.restosapp.domain.GetInitialRestosUseCase
import com.example.myresto.restosapp.domain.ToggleRestoUseCase
import com.example.myresto.restosapp.data.RestosRepository
import com.example.myresto.restosapp.domain.Resto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.*
import javax.inject.Inject

//yang lama masih pake savestatehandle
//class RestoViewModel(private val stateHandle: SavedStateHandle): ViewModel() {
@HiltViewModel
class RestoViewModel @Inject constructor(
    private val getInitialRestosUseCase: GetInitialRestosUseCase,
    private val toggleRestosUseCase: ToggleRestoUseCase
): ViewModel() {
    private val repository = RestosRepository()

    private val _state = mutableStateOf(RestosScreenState(restos = listOf(), isLoading = true))
    private lateinit var restosCall: Call<List<Resto>>
    val state: State<RestosScreenState>
        get() = _state

//    private var restInterface: RestoranApiService
//    private var restoDao = RestoDb.getDaoInstance(RestoAppContext.getAppContext())

    val job = Job()

    //dispatcher main untuk yang gampang"
    //dispatcher IO untuk yang berat (network, disk, dll)
    //dispatcher default untuk CPU intensive (parse json, sort, dll)
    private val scope = CoroutineScope(job + Dispatchers.IO)

    init{

    }


    fun toggleFavorite(id: Int, oldValue: Boolean){

        viewModelScope.launch {
//            val updatedResto = repository.toggleFavoriteRoom(id, oldValue)
            val updatedResto = toggleRestosUseCase(id, oldValue)
            _state.value = _state.value.copy(restos = updatedResto)
        }
    }

//    companion object{
//        const val FAVORITES = "favorites"
//    }

    fun getRestoCoroutine(){
        //opsi lain pake scope viewmodel
        viewModelScope.launch(errorHandler){
//            val restos = repository.getAllRestos()
            val restos = getInitialRestosUseCase()
            _state.value = _state.value.copy(restos = restos, isLoading = false)

            //kalo berkaitan dengan update UI harus pake dispatcher main
        }
    }
    
    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        _state.value = _state.value.copy(
            error = exception.message,
            isLoading = false
        )
    }
}