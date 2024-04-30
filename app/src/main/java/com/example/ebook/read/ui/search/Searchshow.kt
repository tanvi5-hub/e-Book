package com.example.ebook.read.ui.search

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ebook.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Scaffold

import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController



@Composable
fun SearchPage(navController: NavHostController) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), // 设置页面背景为白色
        topBar = {
            RoundedSearchInputBox() // 搜索框组件
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // 在这里添加页面的其他内容

        }
    }
}

@Composable
fun RoundedSearchInputBox() {
    val searchText = remember { mutableStateOf("") }
    OutlinedTextField(
        value = searchText.value,
        onValueChange = { searchText.value = it },
        modifier = Modifier
            .fillMaxWidth() // 使输入框宽度与页面宽度一致
            .padding(16.dp),
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = "Localized description",

                tint = Color.Gray
            )
        },
        label = { Text("Search") },
        singleLine = true,
        shape = RoundedCornerShape(50), // 设置较大的圆角值以获得更圆的效果
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            // 在这里处理搜索逻辑
            // 例如，可以使用 searchText.value 来进行查询
        })
    )
}
@Composable
@Preview
fun SearchInputBox() {
    val context = LocalContext.current
    val searchText = remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchText.value,
        onValueChange = { searchText.value = it },
        modifier = Modifier.padding(16.dp),
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = "Localized description",

                tint = Color.Gray
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(90), // 设置较大的圆角值以获得更圆的效果
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            // 在这里处理搜索逻辑
            // 例如，可以使用 searchText.value 来进行查询
        })
    )
}