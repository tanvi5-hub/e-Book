package com.example.ebook.read.ui.navigation


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.google.accompanist.navigation.animation.composable


import androidx.navigation.compose.rememberNavController
import com.example.ebook.read.ui.home.MainScreen
import com.example.ebook.read.ui.liberary.LibraryScreen
import com.example.ebook.read.ui.personal.PersonScreen
import com.example.ebook.read.ui.search.SearchPage
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navController, startDestination = "home") {
        composable("home") { MainScreen(navController) }
        composable("personal") { PersonScreen(navController) }
        composable("library") { LibraryScreen(navController) }
        composable("search") { SearchPage(navController) }
        // 根据需要添加更多目标
    }
}

