package com.example.myresto.restosapp.data

import com.example.myresto.*
import com.example.myresto.restosapp.RestoAppContext
import com.example.myresto.restosapp.data.local.LocalPartialResto
import com.example.myresto.restosapp.data.local.LocalResto
import com.example.myresto.restosapp.data.local.RestoDao
import com.example.myresto.restosapp.data.local.RestoDb
import com.example.myresto.restosapp.data.remote.RestoranApiService
import com.example.myresto.restosapp.domain.Resto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestosRepository @Inject constructor(
    private val restInterface: RestoranApiService,
    private val restoDao: RestoDao
){
//    private var restInterface: RestoranApiService = Retrofit.Builder()
//        .addConverterFactory(GsonConverterFactory.create())
//        .baseUrl("https://realtimedb-3ef48-default-rtdb.asia-southeast1.firebasedatabase.app/")
//        .build()
//        .create(RestoranApiService::class.java)

//    private var restoDao = RestoDb.getDaoInstance(RestoAppContext.getAppContext())

    suspend fun toggleFavoriteRoom(id: Int, value: Boolean) =
        withContext(Dispatchers.IO){
            restoDao.updateFavorite(
                LocalPartialResto(id = id, isFavorite = value)
            )
//            restoDao.getAll()
        }

    //TAMBAHAN SETELAH PAKAI ROOM
    suspend fun loadRestos(): List<LocalResto>{
        return withContext(Dispatchers.IO){
            try{
                refreshCache()
            }
            catch (e: Exception){
                when (e){
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        if(restoDao.getAll().isEmpty())
                            throw Exception("Data is not available")
                    }
                    else -> throw e
                }
            }
            return@withContext restoDao.getAll()
        }
    }

    suspend fun getRestos(): List<Resto>{
        return withContext(Dispatchers.IO){
            return@withContext restoDao.getAll().map {
                Resto(it.id, it.title, it.description, it.isFavorite)
            }
        }
    }

    private suspend fun refreshCache(){
        //get dari server -> cek di room yang fav
        // -> disimpan ke room -> update ke room
        val remoteRestos = restInterface.getRestaurantsCoroutine()
        val favoriteRestos = restoDao.getAllFavorite()
        restoDao.addAll(remoteRestos.map {
            LocalResto(it.id, it.title, it.description, false)
        })
        restoDao.updateAll(favoriteRestos.map{
            LocalPartialResto(it.id, true)
        })
    }
}