package com.matteo.mybaby2.common.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.matteo.mybaby2.Home
import com.matteo.mybaby2.Tabs
import com.matteo.mybaby2.modules.breastfeedings.components.UpsertBreastFeeding
import com.matteo.mybaby2.modules.poopings.components.UpsertPooping
import java.time.Instant

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val now = Instant.now().toEpochMilli()
    NavHost(navController, startDestination = "${NavigationItem.Activities.route}/$now/${Tabs.BreastFeeding.ordinal}") {
        composable(
            "${NavigationItem.Activities.route}/{date}/{tab}",
            listOf(navArgument("date") { type = NavType.LongType }, navArgument("tab") { type = NavType.IntType })
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getLong("date")
            val tab = backStackEntry.arguments?.getInt("tab")
            Home(navController, date, tab)
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