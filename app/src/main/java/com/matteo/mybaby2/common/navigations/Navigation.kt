package com.matteo.mybaby2.common.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.matteo.mybaby2.modules.activities.components.Activities
import com.matteo.mybaby2.modules.breastfeedings.components.UpsertBreastFeeding
import com.matteo.mybaby2.modules.babies.components.Babies
import com.matteo.mybaby2.modules.poopings.components.UpsertPooping

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavigationItem.Babies.route) {
        composable(NavigationItem.Babies.route) {
            Babies(navController)
        }
        composable(
            "${NavigationItem.Activities.route}/{babyId}",
            arguments = listOf(navArgument("babyId") { type = NavType.IntType })
        ) { backStackEntry ->
            val babyId = backStackEntry.arguments?.getInt("babyId")
            if (babyId == null) {
                throw Exception("babyId is required")
            }
            Activities(navController, babyId.toInt())
        }
        composable(
            "${NavigationItem.Activities.route}/{babyId}/breastfeeding/create",
            arguments = listOf(
                navArgument("babyId") { type = NavType.IntType })
        ) { backStackEntry ->
            val babyId = backStackEntry.arguments?.getInt("babyId")
            if (babyId == null) {
                throw Exception("babyId is required")
            }
            UpsertBreastFeeding(navController, babyId)
        }
        composable(
            "${NavigationItem.Activities.route}/{babyId}/pooping/create",
            arguments = listOf(
                navArgument("babyId") { type = NavType.IntType })
        ) { backStackEntry ->
            val babyId = backStackEntry.arguments?.getInt("babyId")
            if (babyId == null) {
                throw Exception("babyId is required")
            }
            UpsertPooping(navController, babyId)
        }
    }
}