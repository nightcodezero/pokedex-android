package com.nightcode.pokedex.presentation.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nightcode.pokedex.data.remote.model.Pokemon
import com.nightcode.pokedex.data.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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
    private val _pokemonUiState = MutableStateFlow<CommonState<List<Pokemon>>>(CommonState.Idle)
    val pokemonUiState: StateFlow<CommonState<List<Pokemon>>> = _pokemonUiState
    private val offset = MutableStateFlow(0)
    private val _pokemonList = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemonList: StateFlow<List<Pokemon>> = _pokemonList
    private val _favoritePokemon = MutableStateFlow<List<String>>(emptyList())
    val favoritePokemon: StateFlow<List<String>> = _favoritePokemon

    fun fetchPokemonListAndDetail() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _pokemonUiState.value = CommonState.Loading
                repository
                    .fetchPokemonList(offset.value, 20)
                    .shareIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(),
                        replay = 1,
                    ).collect { res ->
                        res
                            .onSuccess { data ->
                                _pokemonList.value = pokemonList.value + data.results
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
            offset.value += 20
            fetchPokemonListAndDetail()
        }
    }

    fun saveFavoritePokemon(name: String) {
        viewModelScope.launch {
            repository.saveFavoritePokemon(name)
            _favoritePokemon.value = repository.getFavoritePokemon()
        }
    }

    fun getFavoritePokemon() {
        viewModelScope.launch {
            _favoritePokemon.value = repository.getFavoritePokemon()
        }
    }

    fun removeFavoritePokemon(name: String) {
        viewModelScope.launch {
            repository.removeFavoritePokemon(name)
            _favoritePokemon.value = repository.getFavoritePokemon()
        }
    }
}