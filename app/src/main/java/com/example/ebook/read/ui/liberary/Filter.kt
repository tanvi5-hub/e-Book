package com.example.ebook.read.ui.liberary

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.material3.BottomAppBar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Button
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ebook.read.ui.theme.ReaderTheme
import com.example.ebook.read.ui.liberary.FilterButton

@Composable
fun FilterButton(navController: NavHostController) {
    var showMenu by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        BottomAppBar {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                // 保持使用 OutlinedButton
                OutlinedButton(
                    onClick = { showMenu = !showMenu },
                    modifier = Modifier.width(150.dp)  // 指定宽度
                ) {
                    Icon(
                        imageVector = Icons.Filled.FilterAlt,
                        contentDescription = "Localized description",
                        tint = Color.Black
                    )
                    Text(text = "Filter books", color = Color.Black)
                }
            }
        }

        // 当菜单展开时显示
        if (showMenu) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(0.dp)
            ) {
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DropdownContent(navController)
                }
            }
        }
    }
}

@Composable
fun DropdownContent(navController: NavHostController) {
    com.google.accompanist.flowlayout.FlowRow(
        mainAxisSpacing = 16.dp,  // 主轴（水平）间距
        crossAxisSpacing = 8.dp,  // 交叉轴（垂直）间距
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        DropdownButton("Filter Option 1", "filter_option_1", navController)
        DropdownButton("Filter Option 2", "library", navController)
        DropdownButton("Filter Option 3", "filter_option_3", navController)
        // 可以继续添加更多按钮
    }
}

@Composable
fun DropdownButton(text: String, route: String, navController: NavHostController) {
    Button(
        onClick = {
            navController.navigate(route)
            var showMenu = false
        },
        modifier = Modifier.padding(horizontal = 0.dp)
    ) {
        Text(text)
    }
}


@Preview
@Composable
fun FilterPreview() {
    val navController = rememberNavController()
    FilterButton(navController)
}