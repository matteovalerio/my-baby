package com.matteo.mybaby2.common.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.matteo.mybaby2.Home
import com.matteo.mybaby2.modules.breastfeedings.components.UpsertBreastFeeding
import com.matteo.mybaby2.modules.poopings.components.UpsertPooping

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavigationItem.Activities.route) {
        composable(
            NavigationItem.Activities.route,
        ) { backStackEntry ->
            Home(navController)
        }
        composable(
            "${NavigationItem.Activities.route}/breastfeeding/create"
        ) { backStackEntry ->
            UpsertBreastFeeding(navController)
        }
        composable(
            "${NavigationItem.Activities.route}/breastfeeding/update/{id}",
            listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            UpsertBreastFeeding(navController, id = id)
        }
        composable(
            "${NavigationItem.Activities.route}/pooping/create"
        ) { backStackEntry ->
            UpsertPooping(navController)
        }
        composable(
            "${NavigationItem.Activities.route}/pooping/update/{id}",
            listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            UpsertPooping(navController, id = id)
        }
    }
}