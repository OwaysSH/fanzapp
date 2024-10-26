package com.fanz.oways.repos.clubs

import com.fanz.oways.model.Club
import com.fanz.oways.model.DataState
import kotlinx.coroutines.flow.Flow

interface IClubsRepository {


    suspend fun getClubs(): Flow<DataState<List<Club>>>
}