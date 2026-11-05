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
import br.com.carlosvillarinho.zelo.ui.DetalheScreen
import br.com.carlosvillarinho.zelo.ui.FavoritosScreen
import br.com.carlosvillarinho.zelo.ui.HomeScreen
import br.com.carlosvillarinho.zelo.ui.NovoRelatoScreen
import br.com.carlosvillarinho.zelo.ui.OnboardingScreen
import br.com.carlosvillarinho.zelo.ui.SplashScreen
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
                onRelatoClick = { id -> navController.navigate(Screen.Detalhe.createRoute(id)) },
                onFavoriteClick = viewModel::toggleFavorite,
                onRetry = viewModel::loadRelatos,
                onNovoRelatoClick = { navController.navigate(Screen.NovoRelato.route) },
                onFavoritosClick = { navController.navigate(Screen.Favoritos.route) }
            )
        }

        composable(
            route = Screen.Detalhe.route,
            arguments = listOf(navArgument(Screen.Detalhe.ARG_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(Screen.Detalhe.ARG_ID) ?: 0
            DetalheScreen(relatoId = id, onBack = { navController.popBackStack() })
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
                onRelatoClick = { id -> navController.navigate(Screen.Detalhe.createRoute(id)) },
                onFavoriteClick = viewModel::toggleFavorite,
                onBack = { navController.popBackStack() }
            )
        }
    }
}