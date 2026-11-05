package br.com.carlosvillarinho.zelo.navigaion

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Detalhe : Screen("detalhe/{relatoId}") {
        const val ARG_ID = "relatoId"
        fun createRoute(id: Int) = "detalhe/$id"
    }
    object NovoRelato : Screen("novo_relato")
    object Favoritos : Screen("favoritos")
}