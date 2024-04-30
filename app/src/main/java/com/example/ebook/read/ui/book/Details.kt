package com.example.ebook.read.ui.book

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ebook.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NovelDetailsPage() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bookname") },
                navigationIcon = {
                    IconButton(onClick = { /* handle back navigation */ }) {
                        androidx.compose.material3.Icon(
                            Icons.Filled.KeyboardReturn,
                            contentDescription = "Localized description",
                            tint = Color.Black
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(backgroundColor = Color.LightGray) {
                Button(modifier = Modifier.weight(1f), onClick = { /* 加入书架逻辑 */ }) {
                    Text("Add to bookstore")
                }
                Button(modifier = Modifier.weight(1f), onClick = { /* 立刻阅读逻辑 */ }) {
                    Text("Read it")
                }
            }
        }
    ) {
        NovelContent()
    }
}

@Composable
fun NovelContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.dog), contentDescription = "书封面")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Book Name", style = MaterialTheme.typography.h5)
        Text("writer", style = MaterialTheme.typography.subtitle1)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Novel introduction", textAlign = TextAlign.Justify)
        Spacer(modifier = Modifier.height(16.dp))
        // 新增的作品种类显示框
        GenreTag(genre = "Fantasy") // 假定作品种类为"Fantasy"
    }
}

@Composable
fun GenreTag(genre: String) {
    Surface(
        color = Color.White, // 设置外层框的背景颜色为浅灰色
        shape = RoundedCornerShape(4.dp), // 设置圆角
        modifier = Modifier
            .fillMaxWidth() // 确保宽度与页面宽度一致
            .border(1.dp, Color.Black, RoundedCornerShape(4.dp)) // 设置黑色边框
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = "Types of the novel:",
                color = Color.Black,
                modifier = Modifier.padding(bottom = 4.dp),
                style = MaterialTheme.typography.body2
            )

            // 使用 Box 来控制内部 Text 的边界
            Box(
                contentAlignment = Alignment.CenterStart, // 文本对齐方式
                modifier = Modifier
                    .wrapContentWidth() // 使包装框宽度与内容宽度一致
            ) {
                Surface(
                    color = Color.LightGray, // 设置内层框的背景颜色为黄色
                    shape = RoundedCornerShape(4.dp), // 设置圆角
                    modifier = Modifier
                        .border(1.dp, Color.Black, RoundedCornerShape(4.dp)) // 设置黑色边框
                ) {
                    Text(
                        text = genre,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewNovelDetailsPage() {
    NovelDetailsPage()
}