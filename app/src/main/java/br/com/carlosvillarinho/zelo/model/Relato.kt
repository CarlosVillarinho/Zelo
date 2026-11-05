package br.com.carlosvillarinho.zelo.model

data class Relato(
    val id: Int,
    val title: String,
    val body: String,
    val favorito: Boolean = false,
    val apoios: Int = 0,
    val apoiado: Boolean = false,
    val meu: Boolean = false
)