package br.com.carlosvillarinho.zelo.ui

import androidx.compose.runtime.Composable

@Composable
fun NovoRelatoScreen(onBack: () -> Unit) {
    SkeletonScreen(
        title = "Novo Relato",
        text = "Tela de Novo Relato",
        onBack = onBack
    )
}