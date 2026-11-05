package br.com.carlosvillarinho.zelo.navigaion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.carlosvillarinho.zelo.model.Onboarding
import br.com.carlosvillarinho.zelo.ui.Screens.DetalheScreen
import br.com.carlosvillarinho.zelo.ui.Screens.FavoritosScreen
import br.com.carlosvillarinho.zelo.ui.Screens.HomeScreen
import br.com.carlosvillarinho.zelo.ui.Screens.NovoRelatoScreen
import br.com.carlosvillarinho.zelo.ui.Screens.OnboardingScreen
import br.com.carlosvillarinho.zelo.ui.Screens.PerfilScreen
import br.com.carlosvillarinho.zelo.ui.Screens.PrioridadesScreen
import br.com.carlosvillarinho.zelo.ui.Screens.SplashScreen
import br.com.carlosvillarinho.zelo.viewmodel.UiState
import br.com.carlosvillarinho.zelo.viewmodel.ZeloViewModel

@Composable
fun ZeloNavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: ZeloViewModel = viewModel()
) {
    val context = LocalContext.current
    val prefs = remember { Onboarding(context) }

    val uiState by viewModel.uiState.collectAsState()
    val favoritos by viewModel.favoritos.collectAsState()
    val prioridades by viewModel.prioridades.collectAsState()
    val perfil by viewModel.perfil.collectAsState()

    val onBottomNavigate: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(Screen.Home.route) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
    val abrirDetalhe: (Int) -> Unit = { id ->
        navController.navigate(Screen.Detalhe.createRoute(id))
    }

    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        composable(Screen.Splash.route) {
            SplashScreen(
                onTimeout = {
                    val destination =
                        if (prefs.hasSeenOnboarding()) Screen.Home.route else Screen.Onboarding.route
                    navController.navigate(destination) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    prefs.setOnboardingSeen()
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                uiState = uiState,
                onRelatoClick = abrirDetalhe,
                onFavoriteClick = viewModel::toggleFavorite,
                onApoiarClick = viewModel::toggleApoio,
                onRetry = viewModel::loadRelatos,
                onNovoRelatoClick = { navController.navigate(Screen.NovoRelato.route) },
                onFavoritosClick = { navController.navigate(Screen.Favoritos.route) },
                bottomBar = {
                    ZeloBottomBar(selectedRoute = Screen.Home.route, onNavigate = onBottomNavigate)
                }
            )
        }

        composable(Screen.Prioridades.route) {
            PrioridadesScreen(
                prioridades = prioridades,
                onRelatoClick = abrirDetalhe,
                onFavoriteClick = viewModel::toggleFavorite,
                onApoiarClick = viewModel::toggleApoio,
                bottomBar = {
                    ZeloBottomBar(selectedRoute = Screen.Prioridades.route, onNavigate = onBottomNavigate)
                }
            )
        }

        composable(Screen.Perfil.route) {
            PerfilScreen(
                perfil = perfil,
                onRelatoClick = abrirDetalhe,
                onFavoriteClick = viewModel::toggleFavorite,
                onApoiarClick = viewModel::toggleApoio,
                bottomBar = {
                    ZeloBottomBar(selectedRoute = Screen.Perfil.route, onNavigate = onBottomNavigate)
                }
            )
        }

        composable(
            route = Screen.Detalhe.route,
            arguments = listOf(navArgument(Screen.Detalhe.ARG_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(Screen.Detalhe.ARG_ID) ?: 0
            val relato = (uiState as? UiState.Success)?.findById(id)
            DetalheScreen(
                relato = relato,
                onFavoriteClick = viewModel::toggleFavorite,
                onApoiarClick = viewModel::toggleApoio,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.NovoRelato.route) {
            NovoRelatoScreen(
                onPublicar = viewModel::addRelato,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Favoritos.route) {
            FavoritosScreen(
                favoritos = favoritos,
                onRelatoClick = abrirDetalhe,
                onFavoriteClick = viewModel::toggleFavorite,
                onApoiarClick = viewModel::toggleApoio,
                onBack = { navController.popBackStack() }
            )
        }
    }
}