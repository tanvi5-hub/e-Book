package com.example.ebook.read.ui.book

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNovelDetailsPage() {
    NovelDetailsPage()
}