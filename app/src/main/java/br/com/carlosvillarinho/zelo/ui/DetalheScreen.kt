package br.com.carlosvillarinho.zelo.ui

import androidx.compose.runtime.Composable

@Composable
fun DetalheScreen(relatoId: Int, onBack: () -> Unit) {
    SkeletonScreen(
        title = "Detalhe",
        text = "Detalhe do relato #$relatoId",
        onBack = onBack
    )
}