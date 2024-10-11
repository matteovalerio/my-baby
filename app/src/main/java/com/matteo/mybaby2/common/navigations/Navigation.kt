package com.matteo.mybaby2.common.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
            "${NavigationItem.Activities.route}/pooping/create"
        ) { backStackEntry ->
            UpsertPooping(navController)
        }
    }
}