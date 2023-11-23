package com.agilizzulhaq.submissionakhiraplikasiandroid

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.agilizzulhaq.submissionakhiraplikasiandroid.navigation.NavigationItem
import com.agilizzulhaq.submissionakhiraplikasiandroid.navigation.Screen
import com.agilizzulhaq.submissionakhiraplikasiandroid.ui.screen.about.AboutScreen
import com.agilizzulhaq.submissionakhiraplikasiandroid.ui.screen.detail.DetailScreen
import com.agilizzulhaq.submissionakhiraplikasiandroid.ui.screen.favorite.FavoriteScreen
import com.agilizzulhaq.submissionakhiraplikasiandroid.ui.screen.home.HomeScreen

@Composable
fun MyValorant(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailAgent.route) {
                BottomNavBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen { agentId ->
                    navController.navigate(Screen.DetailAgent.createRoute(agentId))
                }
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    navigateToDetail = { agentId ->
                        navController.navigate(Screen.DetailAgent.createRoute(agentId))
                    }
                )
            }
            composable(Screen.About.route) {
                AboutScreen()
            }
            composable(
                route = Screen.DetailAgent.route,
                arguments = listOf(
                    navArgument("agentId") { type = NavType.IntType }
                )
            ) {
                val id = it.arguments?.getInt("agentId") ?: -1
                DetailScreen(
                    agentId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomNavBar(navController: NavHostController, modifier: Modifier = Modifier) {
    BottomNavigation(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.favorite),
                icon = Icons.Default.Star,
                screen = Screen.Favorite
            ),
            NavigationItem(
                title = stringResource(R.string.about_me),
                icon = Icons.Default.Person,
                screen = Screen.About
            ),
        )
        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}