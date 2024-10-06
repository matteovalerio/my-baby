package com.matteo.mybaby2.common.navigations


enum class Screen {
    BABIES, ACTIVITIES
}

sealed class NavigationItem(val route: String) {
    object Babies : NavigationItem(Screen.BABIES.name)
    object Activities : NavigationItem(Screen.ACTIVITIES.name)
}