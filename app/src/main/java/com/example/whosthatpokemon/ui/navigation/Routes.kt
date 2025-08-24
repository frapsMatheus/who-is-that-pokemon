package com.example.whosthatpokemon.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface BaseRoute {
    val routeName: String

}

object PickGeneration : BaseRoute {
    override val routeName = "PickGeneration"
}
object Challenge : BaseRoute {
    override val routeName = "Challenge"
    const val generationArg = "generationId"
    val routeWithArgs = "${routeName}/{${generationArg}}"
    val arguments = listOf(
        navArgument(generationArg) { type = NavType.IntType }
    )
}