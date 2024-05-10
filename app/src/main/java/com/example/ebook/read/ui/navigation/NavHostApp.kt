package com.example.ebook.read.ui.navigation


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.google.accompanist.navigation.animation.composable


import androidx.navigation.compose.rememberNavController
import com.example.ebook.read.ui.bestwriter.BestwriterScreen
import com.example.ebook.read.ui.book.NovelDetailsPage
import com.example.ebook.read.ui.home.MainScreen
import com.example.ebook.read.ui.liberary.LibraryScreen
import com.example.ebook.read.ui.personal.PersonScreen
import com.example.ebook.read.ui.search.SearchPage
import com.example.ebook.read.ui.version.VersionScreen
import com.example.ebook.read.ui.writer.AuthorContent
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.FirebaseUser
import com.example.ebook.read.ui.writer.AuthorDetailsPage
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navController, startDestination = "home") {
        composable("home") { MainScreen(navController) }
        composable("personal") { PersonScreen(navController) }
        composable("library") { LibraryScreen(navController) }
        composable("search") { SearchPage(navController) }
        composable("version"){ VersionScreen(navController)}
        composable("Bestwriter"){ BestwriterScreen(navController)}
        composable("bookDetails/{bookId}") { backStackEntry ->
            // 从路由参数中获取书籍ID
            val bookId = backStackEntry.arguments?.getString("bookId")
            if (bookId != null) {
                NovelDetailsPage(bookId, navController)
            }
        }
        composable("writerDetails/{authorId}") { backStackEntry ->
            // 从路由参数中获取书籍ID
            val authorId= backStackEntry.arguments?.getString("authorId")
            if (authorId != null) {
                AuthorDetailsPage(authorId, navController)
            }
        }
        // 根据需要添加更多目标
    }
}

