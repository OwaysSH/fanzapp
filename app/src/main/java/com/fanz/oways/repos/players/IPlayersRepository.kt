package com.fanz.oways.repos.players

import com.fanz.oways.model.DataState
import com.fanz.oways.model.Player
import kotlinx.coroutines.flow.Flow

interface IPlayersRepository {

    suspend fun getAllPlayers(): Flow<DataState<List<Player>>>

    suspend fun getPlayersSortedByName(): Flow<DataState<List<Player>>>

    suspend fun getPlayersSortedByPriceDesc(): Flow<DataState<List<Player>>>

    suspend fun getPlayersByPosition(positionName: String): Flow<DataState<List<Player>>>

    suspend fun getPlayersByClubCode(clubCode: String): Flow<DataState<List<Player>>>

    suspend fun getPlayersByCardType(cardType: String): Flow<DataState<List<Player>>>

}