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

import androidx.compose.material3.BottomAppBar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.filled.FilterAlt
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ebook.read.ui.theme.ReaderTheme

@Composable
fun FilterButton(navController: NavHostController) {
    BottomAppBar {
        // 使用 Row 来水平排列按钮
        Row(
            modifier = Modifier.fillMaxWidth(), // 让 Row 填充整个 BottomAppBar 的宽度
            horizontalArrangement = Arrangement.End // 将内容对齐到末端位置
        ) {
            var showMenu by remember { mutableStateOf(false) }  // 记住菜单的展开状态

            // 按钮
            OutlinedButton(
                onClick = { showMenu = !showMenu },  // 点击时切换菜单显示状态
                modifier = Modifier.width(150.dp) // 设置按钮宽度
            ) {
                Icon(
                    imageVector = Icons.Filled.FilterAlt,
                    contentDescription = "Localized description",
                    tint = Color.Black
                )
                Text(text = "Filter books", color = Color.Black)
            }

            // 下拉菜单
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }  // 点击外部时关闭菜单
            ) {
                DropdownMenuItem(
                    onClick = {
                        // 定义点击菜单项后的行为
                        navController.navigate("filter_option_1")
                        showMenu = false  // 关闭菜单
                    }
                ) {
                    Text("Filter Option 1")
                }
                DropdownMenuItem(
                    onClick = {
                        // 定义点击菜单项后的行为
                        navController.navigate("library")
                        showMenu = false  // 关闭菜单
                    }
                ) {
                    Text("Filter Option 2")
                }
                DropdownMenuItem(
                    onClick = {
                        // 定义点击菜单项后的行为
                        navController.navigate("filter_option_3")
                        showMenu = false  // 关闭菜单
                    }
                ) {
                    Text("Filter Option 3")
                }
            }
        }
    }
}
@Preview
@Composable
fun FilterPreview() {
    ReaderTheme {
        // 确保传递一个有效的NavHostController实例
        val navController = rememberNavController()
        FilterButton(navController)
    }
}
