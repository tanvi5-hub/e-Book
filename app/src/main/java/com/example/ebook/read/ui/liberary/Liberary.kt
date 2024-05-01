package com.example.ebook.read.ui.liberary

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ebook.read.ui.home.HomeBottomAppBarExample
import com.example.ebook.read.ui.home.SearchResult
import com.example.ebook.read.ui.search.SearchButton
import com.example.ebook.read.ui.theme.ReaderTheme

import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            Column {
                SearchButton(navController)
                FilterButton(navController)
            }
             // 确保你有一个SearchButton组件的定义
        },
        bottomBar = {
            HomeBottomAppBarExample(navController) // 使用外部传入的navController
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())) {
            SearchResult(onClick = {
                // 定义点击搜索结果后的行为，例如导航到详细页面
                // 例如：navController.navigate("detail_screen")
            })
            // SearchResult组件需要定义一个onClick参数的行为
            Spacer(Modifier.height(8.dp)) // 根据需要调整间距
        }
    }
}



@Preview
@Composable
fun PreviewSearchResult() {
    ReaderTheme {
        // 确保传递一个有效的NavHostController实例
        val navController = rememberNavController()
        LibraryScreen(navController)
    }

}