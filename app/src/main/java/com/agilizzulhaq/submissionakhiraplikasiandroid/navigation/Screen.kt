package com.agilizzulhaq.submissionakhiraplikasiandroid.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object About : Screen("about")
    object DetailAgent : Screen("home/{agentId}") {
        fun createRoute(agentId: Int) = "home/$agentId"
    }
}