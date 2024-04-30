package com.example.ebook.read.ui.navigation


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.composable


import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import com.example.ebook.read.ui.home.MainScreen
import com.example.ebook.read.ui.personal.PersonScreen
import com.example.ebook.read.ui.search.SearchPage

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { MainScreen(navController) }
        composable("personal") { PersonScreen(navController) }
        composable("liberary") { PersonScreen(navController) }
        composable("search") {SearchPage(navController)}
        // 添加更多的目标
    }
}

