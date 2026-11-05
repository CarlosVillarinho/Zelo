package br.com.carlosvillarinho.zelo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.carlosvillarinho.zelo.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException
import br.com.carlosvillarinho.zelo.model.Relato
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ZeloViewModel : ViewModel() {
    private val service = RetrofitClient.relatoService

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    val favoritos: StateFlow<List<Relato>> = _uiState
        .map { state ->
            if (state is UiState.Success) state.relatos.filter { it.isFavorite } else emptyList()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        loadRelatos()
    }

    fun loadRelatos() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val relatos = service.getRelatos()
                _uiState.value = UiState.Success(relatos)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Erro ao carregar os relatos")
            }
        }
    }

    fun toggleFavorite(id: Int) {
        _uiState.update { state ->
            if (state is UiState.Success) {
                state.copy(
                    relatos = state.relatos.map { relato ->
                        if (relato.id == id) relato.copy(isFavorite = !relato.isFavorite) else relato
                    }
                )
            } else {
                state
            }
        }
    }

    fun addRelato(title: String, body: String): Boolean {
        val titulo = title.trim()
        val corpo = body.trim()
        if (titulo.isEmpty() || corpo.isEmpty()) return false

        val atual = _uiState.value as? UiState.Success ?: return false
        val novo = Relato(
            id = (atual.relatos.maxOfOrNull { it.id } ?: 0) + 1,
            title = titulo,
            body = corpo
        )
        _uiState.update { state ->
            if (state is UiState.Success) {
                state.copy(relatos = listOf(novo) + state.relatos)
            } else {
                state
            }
        }
        return true
    }
}