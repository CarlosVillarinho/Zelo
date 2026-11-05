package br.com.carlosvillarinho.zelo.viewmodel

import br.com.carlosvillarinho.zelo.model.Relato

data class PerfilUi(
    val meusRelatos: List<Relato> = emptyList(),
    val totalFavoritos: Int = 0,
    val apoiosDados: Int = 0
)