package com.example.whosthatpokemon.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.whosthatpokemon.ui.composables.organism.PickGenerationScreen.PickGenerationScreen
import androidx.navigation.compose.composable
import com.example.whosthatpokemon.ui.composables.organism.Challenge.ChallengePage
import com.example.whosthatpokemon.ui.composables.organism.Challenge.ChallengeViewModel
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import kotlin.getValue

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

@Composable
fun MainNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = PickGeneration.routeName,
        modifier = modifier
    ) {
        composable(route = PickGeneration.routeName) {
            PickGenerationScreen(
                navigateToChallenge = { it ->
                    Log.d("TAG", "navigateToChallenge: $it")
                    navController.navigateSingleTopTo("${Challenge.routeName}/${it}")
                }
            )
        }
        composable(
            route = Challenge.routeWithArgs,
            arguments =  Challenge.arguments
        ) { navBackStackEntry ->
            // Retrieve the passed argument
            val generation =
                navBackStackEntry.arguments?.getInt(Challenge.generationArg)

            Log.d("TAG", "MainNavigation: $generation")
            val viewModel: ChallengeViewModel = hiltViewModel()
            // Pass accountType to SingleAccountScreen
            ChallengePage(viewModel)
        }
    }
}