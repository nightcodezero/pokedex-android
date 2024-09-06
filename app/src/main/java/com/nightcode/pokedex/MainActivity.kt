package com.nightcode.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nightcode.pokedex.presentation.theme.PokedexTheme
import com.nightcode.pokedex.presentation.ui.dashboard.DashboardScreen
import com.nightcode.pokedex.presentation.ui.info.PokemonInfoScreen
import com.nightcode.pokedex.presentation.ui.info.PokemonInfoScreenRoute

@OptIn(ExperimentalSharedTransitionApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokedexTheme {
                SharedTransitionLayout {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = DashboardScreen) {
                        composable<DashboardScreen> {
                            DashboardScreen(
                                navController = navController,
                                animatedVisibilityScope = this,
                            )
                        }
                        composable<PokemonInfoScreenRoute>(
                            typeMap = PokemonInfoScreenRoute.typeMap,
                        ) {
                            val args = it.toRoute<PokemonInfoScreenRoute>()
                            PokemonInfoScreen(
                                args.pokemon,
                                navController = navController,
                                animatedVisibilityScope = this,
                            )
                        }
                    }
                }
            }
        }
    }
}