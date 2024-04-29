package com.example.ebook.read.ui.personal

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.FindInPage
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.OffsetEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.*
import androidx.compose.foundation.text.*
import androidx.compose.foundation.layout.*
import com.example.ebook.R


@Composable
fun MediumBottomApp(onClick: () -> Unit) {
    var editableText by remember { mutableStateOf(TextFieldValue()) }
    var isEditing by remember { mutableStateOf(false) }

    Column() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth() // 使Row充满屏幕宽度
                .padding(0.dp) // 添加一些内边距
                .border(0.1.dp, Color.Black)
                .clickable {
                    if (!isEditing) {
                        isEditing = true
                        editableText = TextFieldValue("")
                    }
                }
        ) {
            Spacer(Modifier.width(0.dp))

            Column {
                Text(
                    if (isEditing) "Editing Introduction" else "Introduction",
                    fontSize = 32.sp, // 明确指定字体大小
                    color = if (isEditing) Color.Gray else Color.Black // 根据编辑状态设置颜色
                )
            }
        }

        Spacer(Modifier.height(8.dp)) // 添加一些垂直间距

        // 添加可编辑的文本
        BasicTextField(
            value = editableText,
            onValueChange = { editableText = it },
            textStyle = TextStyle(color = if (isEditing) Color.Gray else Color.Black) // 根据编辑状态设置文本颜色
        )
    }
}
@Composable
fun MediumBottomwriter(onClick: () -> Unit) {


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth() // 使Row充满屏幕宽度
                .padding(0.dp) // 添加一些内边距
                .border(0.1.dp, Color.Black)
                .clickable(onClick = onClick)
        ) {

            Spacer(Modifier.width(0.dp))

            Column {
                Text(
                    "Favorite Authors",
                    fontSize = 32.sp // 明确指定字体大小
                )

            }
        }

    }
@Composable
fun MediumBottomset(onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth() // 使Row充满屏幕宽度
            .padding(0.dp) // 添加一些内边距
            .border(0.1.dp, Color.Black)
            .drawWithContent {
                drawContent()
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 0.1.dp.toPx()
                )
            }
            .clickable(onClick = onClick)
    ) {
        Spacer(Modifier.width(0.1.dp))

        Column {
            Text(
                "Software version",
                fontSize = 32.sp // 明确指定字体大小
            )
        }
    }
}
@Preview
@Composable
fun PreviewSearchResult() {
    MediumBottomApp(onClick={})
    MediumBottomwriter(onClick={})
}

