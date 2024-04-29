package com.example.ebook.read.ui.read


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.height


import androidx.compose.foundation.layout.*
import com.example.ebook.read.ui.home.SearchResult

@Preview
@Composable

fun MainScreen() {
    Scaffold(
        topBar = {
            Clickback()
        },
        bottomBar = {
            Clickscreen() // 这里调用您的底部导航栏
        }


    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TxtFileContent("C:/Users/23227/Desktop/read/pin.txt")
            // 在这里调用SearchResult函数
            Spacer(Modifier.height(0.dp)) // 添加一些垂直间距

        }
    }
}


