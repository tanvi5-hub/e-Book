package com.example.ebook.read.ui.bestwriter

import android.annotation.SuppressLint
import com.example.ebook.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ebook.read.model.SearchViewModel
import com.example.ebook.read.ui.theme.ReaderTheme


@Composable
fun BestwriterButton(navController: NavHostController, viewModel: SearchViewModel = viewModel()) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth() // 使Row充满屏幕宽度
            .padding(0.dp) // 添加一些内边距
            .border(0.3.dp, Color.Black)

    ) {
        TextButton(onClick = { navController.navigate("search") }) {
            Image(painter = painterResource(id = R.drawable.dog), contentDescription = "作者头像",contentScale = ContentScale.Crop, modifier = Modifier
                .size(70.dp)
                // 设置外部容器大小，这将是圆形的直径
                .clip(CircleShape) )
            Spacer(Modifier.width(20.dp))

            Column {
                androidx.compose.material3.Text(
                    viewModel.imageName,
                    fontSize = 32.sp // 明确指定字体大小
                )

            }
            Spacer(Modifier.width(2000.dp))
        }

        }



}


@Preview(showBackground = true)
@Composable
fun BestWriterbuttonContent() {
    ReaderTheme {
        // 确保传递一个有效的NavHostController实例
        val navController = rememberNavController()
        BestwriterButton(navController)
    }
}