package com.example.ebook.read.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ebook.R
import androidx.compose.foundation.border

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import com.example.ebook.read.ui.personal.ImageButton

// [START android_compose_layout_basics_1]

@Composable



fun SearchResult(onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth() // 使Row充满屏幕宽度
            .padding(0.dp) // 添加一些内边距
            .border(0.1.dp, Color.Black)
            .clickable(onClick = onClick )
    ) {
        Image(
            painter = painterResource(id = R.drawable.dog),
            contentDescription = "",
            modifier = Modifier.size(80.dp)
        )
        Spacer(Modifier.width(0.dp))

        Column {
            Text(
                "dog",
                fontSize = 32.sp // 明确指定字体大小
            )
            Text("Read up to Chapter 1234" ,
                fontSize = 16.sp
            )
        }
    }

}

@Preview
@Composable
fun PreviewSearchResult() {
    SearchResult(onClick={})
}
