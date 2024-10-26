package com.fanz.oways.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fanz.oways.model.Club
import com.fanz.oways.model.DataState
import com.fanz.oways.model.Player
import com.fanz.oways.repos.clubs.IClubsRepository
import com.fanz.oways.repos.players.IPlayersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DraftTeamViewModel @Inject constructor(
    private val clubRepository: IClubsRepository,
    private val playersRepository: IPlayersRepository
) : BaseViewModel() {


    private val _clubList: MutableLiveData<List<Club>?> = MutableLiveData()
    val clubList: LiveData<List<Club>?> get() = _clubList


    private val _playerList: MutableLiveData<List<Player>?> = MutableLiveData()
    val playerList: LiveData<List<Player>?> get() = _playerList

    fun onEvent(event: ClubEvents) {
        viewModelScope.launch {
            when (event) {
                ClubEvents.GetClubs -> {
                    clubRepository.getClubs().collectLatest { dataState ->
                        when (dataState) {
                            is DataState.Empty -> {
                                _clubList.postValue(arrayListOf())

                            }

                            is DataState.Error -> {
                                _clubList.postValue(arrayListOf())

                            }

                            is DataState.Loading -> {

                            }

                            is DataState.Success -> {
                                _clubList.postValue(dataState.data)
                            }
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: PlayerEvents) {
        viewModelScope.launch {
            when (event) {
                PlayerEvents.GetAllPlayers -> {
                    playersRepository.getAllPlayers().collectLatest { dataState ->
                        when (dataState) {
                            is DataState.Empty -> {
                                _playerList.postValue(arrayListOf())
                            }

                            is DataState.Error -> {
                                _playerList.postValue(arrayListOf())
                            }

                            is DataState.Loading -> {
                            }

                            is DataState.Success -> {
                                _playerList.postValue(dataState.data)
                            }
                        }
                    }
                }

                is PlayerEvents.GetPlayersByPosition -> {
                    playersRepository.getPlayersByPosition(event.positionName).collectLatest { dataState ->
                        when (dataState) {
                            is DataState.Empty -> {
                                _playerList.postValue(arrayListOf())
                            }

                            is DataState.Error -> {
                                _playerList.postValue(arrayListOf())
                            }

                            is DataState.Loading -> {
                            }

                            is DataState.Success -> {
                                _playerList.postValue(dataState.data)
                            }
                        }
                    }

                }
            }
        }
    }

    sealed class ClubEvents {
        data object GetClubs : ClubEvents()

    }

    sealed class PlayerEvents {
        data class GetPlayersByPosition(val positionName: String) : PlayerEvents()

        data object GetAllPlayers : PlayerEvents()

    }
}