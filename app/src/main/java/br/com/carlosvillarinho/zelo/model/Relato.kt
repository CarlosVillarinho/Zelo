package br.com.carlosvillarinho.zelo.model

data class Relato(
    val id: Int,
    val title: String,
    val body: String,
    val isFavorite: Boolean = false
)