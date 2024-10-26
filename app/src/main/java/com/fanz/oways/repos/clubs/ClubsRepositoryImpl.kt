package com.fanz.oways.repos.clubs

import android.content.Context
import com.fanz.oways.model.Club
import com.fanz.oways.model.DataState
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ClubsRepositoryImpl(private val context: Context, private val service: DatabaseReference) : IClubsRepository {


    override suspend fun getClubs(): Flow<DataState<List<Club>>>  = flow {

        val snapshot = service.get().await()
        try {
            val clubs = snapshot.children.mapNotNull {
                it.getValue(Club::class.java)
            }
            if (clubs.isNullOrEmpty()){
                emit(DataState.Empty)
            }else {
                emit(DataState.Success(clubs, 200))
            }
        }catch (e: Exception){
            emit(DataState.Error(e))

        }

    }


}