package com.matteo.mybaby2.common.navigations


enum class Screen { ACTIVITIES
}

sealed class NavigationItem(val route: String) {
    object Activities : NavigationItem(Screen.ACTIVITIES.name)
}