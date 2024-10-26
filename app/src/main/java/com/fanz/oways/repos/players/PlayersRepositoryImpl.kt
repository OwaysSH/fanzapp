package com.fanz.oways.repos.players

import android.content.Context
import com.fanz.oways.model.DataState
import com.fanz.oways.model.Player
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class PlayersRepositoryImpl(private val context: Context, private val service: DatabaseReference) : IPlayersRepository {

    override suspend fun getAllPlayers(): Flow<DataState<List<Player>>>  = flow {
        val snapshot = service.get().await()
        try {
            val list = snapshot.children.mapNotNull {
                it.getValue(Player::class.java)
            }
            if (list.isEmpty()){
                emit(DataState.Empty)
            }else {
                emit(DataState.Success(list, 200))
            }
        }catch (e: Exception){

            emit(DataState.Error(e))

        }
    }

    override suspend fun getPlayersSortedByName(): Flow<DataState<List<Player>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlayersSortedByPriceDesc(): Flow<DataState<List<Player>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlayersByPosition(positionName: String): Flow<DataState<List<Player>>> = flow {
        val snapshot = service.get().await()
        try {
            val list = snapshot.children.mapNotNull {
                it.getValue(Player::class.java)
            }
            val filteredList = list.filter { it.position?.uppercase() == positionName }

            if (filteredList.isEmpty()){
                emit(DataState.Empty)
            }else {
                emit(DataState.Success(filteredList, 200))
            }
        }catch (e: Exception){
            emit(DataState.Error(e))

        }
    }

    override suspend fun getPlayersByClubCode(clubCode: String): Flow<DataState<List<Player>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlayersByCardType(cardType: String): Flow<DataState<List<Player>>> {
        TODO("Not yet implemented")
    }
}