package com.example.ebook.read.ui.personal

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.height


import androidx.compose.foundation.layout.fillMaxWidth
import com.example.ebook.read.ui.theme.ReaderTheme
import androidx.compose.foundation.layout.*

import com.example.ebook.read.ui.home.HomeBottomAppBarExample
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.background
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun PersonScreen(
    navController: NavHostController, // 正确的类型声明
    modifier: Modifier = Modifier // 正确的Modifier声明
) {
    Scaffold(
        bottomBar = {
            HomeBottomAppBarExample(navController) // 使用外部传入的navController
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            ImageButton(onClick = { /* 定义点击ImageButton后的行为 */ })
            MediumBottomApp(onClick = { /* 定义点击MediumBottomApp后的行为 */ })
            MediumBottomwriter(onClick = { navController.navigate("Bestwriter") })
            MediumBottomset(onClick = { navController.navigate("version") })

            Spacer(Modifier.height(8.dp)) // 根据需要调整间距

            // 在MediumBottomset下方添加一条黑线

        }
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
fun HomePreview() {
    ReaderTheme {
        // 确保传递一个有效的NavHostController实例
        val navController = rememberNavController()
        PersonScreen(navController)
    }
}