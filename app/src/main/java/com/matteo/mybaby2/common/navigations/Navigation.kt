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
    NavHost(navController, startDestination = NavigationItem.Activities.route) {
        composable(NavigationItem.Babies.route) {
            Babies(navController)
        }
        composable(
            NavigationItem.Activities.route,
        ) { backStackEntry ->
            Activities(navController)
        }
        composable(
            "${NavigationItem.Activities.route}/breastfeeding/create"
        ) { backStackEntry ->
            UpsertBreastFeeding(navController)
        }
        composable(
            "${NavigationItem.Activities.route}/pooping/create"
        ) { backStackEntry ->
            UpsertPooping(navController)
        }
    }
}