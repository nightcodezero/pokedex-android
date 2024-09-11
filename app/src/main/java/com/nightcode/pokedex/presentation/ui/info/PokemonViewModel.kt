package com.nightcode.pokedex.presentation.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nightcode.pokedex.data.network.model.PokemonDTO
import com.nightcode.pokedex.data.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class CommonState<out T> {
    data object Idle : CommonState<Nothing>()

    data object Loading : CommonState<Nothing>()

    data class Success<T>(
        val data: T,
    ) : CommonState<T>()

    data class Error(
        val message: String,
    ) : CommonState<Nothing>()
}

class PokemonViewModel(
    private val repository: HomeRepository,
) : ViewModel() {
    private val _pokemonUiState = MutableStateFlow<CommonState<List<PokemonDTO>>>(CommonState.Idle)
    val pokemonUiState: StateFlow<CommonState<List<PokemonDTO>>> = _pokemonUiState
    private val _offset = MutableStateFlow(0)
    private val _pokemonList = MutableStateFlow<List<PokemonDTO>>(emptyList())
    val pokemonList: StateFlow<List<PokemonDTO>> = _pokemonList

    fun fetchPokemonListAndDetail() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _pokemonUiState.value = CommonState.Loading
                repository
                    .fetchPokemonList(_offset.value, 20)
                    .shareIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(),
                        replay = 1,
                    ).collect { res ->
                        res
                            .onSuccess { data ->
                                data.results.forEach { result ->
                                    result.name.let { name ->
                                        _pokemonList.value =
                                            pokemonList.value.toMutableList().apply {
                                                add(
                                                    PokemonDTO(
                                                        1,
                                                        name = name,
                                                        imageUrl = result.imageUrl,
                                                        bgColor = "black",
                                                    ),
                                                )
                                            }
                                    }
                                }
                                _pokemonUiState.value = CommonState.Success(pokemonList.value)
                            }.onFailure { err ->
                                _pokemonUiState.value =
                                    CommonState.Error(err.message ?: "An error occurred")
                            }
                    }
            }
        }
    }

    fun fetchMorePokemon() {
        viewModelScope.launch {
            _offset.value += 20
            fetchPokemonListAndDetail()
        }
    }
}