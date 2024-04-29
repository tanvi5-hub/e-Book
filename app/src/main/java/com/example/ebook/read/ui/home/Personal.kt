package com.example.ebook.read.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.height


import androidx.compose.foundation.layout.*

import com.example.ebook.read.ui.search.SearchButton
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
@Preview
@Composable

fun MainScreen() {
    Scaffold(
        topBar = {
            SearchButton()
        },
        bottomBar = {
            HomeBottomAppBarExample() // 这里调用您的底部导航栏
        }


    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).verticalScroll(rememberScrollState())) {
            SearchResult(onClick = {})
            // 在这里调用SearchResult函数
            Spacer(Modifier.height(0.dp)) // 添加一些垂直间距

        }
    }
}



