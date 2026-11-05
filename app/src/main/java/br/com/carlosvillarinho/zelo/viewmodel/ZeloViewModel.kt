package br.com.carlosvillarinho.zelo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.carlosvillarinho.zelo.model.Relato
import br.com.carlosvillarinho.zelo.model.RelatosZelo
import br.com.carlosvillarinho.zelo.network.RetrofitClient
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ZeloViewModel : ViewModel() {

    private val service = RetrofitClient.relatoService

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    val favoritos: StateFlow<List<Relato>> = _uiState
        .map { state ->
            if (state is UiState.Success) state.relatos.filter { it.favorito } else emptyList()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val prioridades: StateFlow<List<Relato>> = _uiState
        .map { state ->
            if (state is UiState.Success) state.relatos.sortedByDescending { it.apoios } else emptyList()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val perfil: StateFlow<PerfilUi> = _uiState
        .map { state ->
            if (state is UiState.Success) {
                PerfilUi(
                    meusRelatos = state.relatos.filter { it.meu },
                    totalFavoritos = state.relatos.count { it.favorito },
                    apoiosDados = state.relatos.count { it.apoiado }
                )
            } else {
                PerfilUi()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PerfilUi())

    init {
        loadRelatos()
    }

    fun loadRelatos() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val relatos = service.getRelatos().zip(RelatosZelo.exemplos) { relato, exemplo ->
                    relato.copy(
                        title = exemplo.title,
                        body = exemplo.body,
                        apoios = exemplo.apoios
                    )
                }
                _uiState.value = UiState.Success(relatos)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Erro ao carregar os relatos")
            }
        }
    }

    fun toggleFavorite(id: Int) {
        atualizarRelato(id) { it.copy(favorito = !it.favorito) }
    }

    fun toggleApoio(id: Int) {
        atualizarRelato(id) { relato ->
            if (relato.apoiado) {
                relato.copy(apoiado = false, apoios = relato.apoios - 1)
            } else {
                relato.copy(apoiado = true, apoios = relato.apoios + 1)
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
            body = corpo,
            meu = true
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

    private fun atualizarRelato(id: Int, transform: (Relato) -> Relato) {
        _uiState.update { state ->
            if (state is UiState.Success) {
                state.copy(
                    relatos = state.relatos.map { relato ->
                        if (relato.id == id) transform(relato) else relato
                    }
                )
            } else {
                state
            }
        }
    }
}
