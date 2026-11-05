package br.com.carlosvillarinho.zelo.viewmodel

import br.com.carlosvillarinho.zelo.model.Relato

sealed class UiState {
    object Initial : UiState()
    object Loading : UiState()
    data class Success(val relatos: List<Relato>) : UiState()
    data class Error(val message: String) : UiState()
}