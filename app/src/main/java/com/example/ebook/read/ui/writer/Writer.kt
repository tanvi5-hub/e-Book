package com.example.ebook.read.ui.writer
import android.annotation.SuppressLint
import com.example.ebook.R
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AuthorDetailsPage() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Writer Introducion") },
                navigationIcon = {
                    IconButton(onClick = { /* handle back navigation */ }) {
                        androidx.compose.material3.Icon(
                            Icons.Filled.KeyboardReturn,
                            contentDescription = "Localized description",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) {
        AuthorContent()
    }
}

@Composable
fun AuthorContent() {
    var isFollowing by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.dog), contentDescription = "作者头像",contentScale = ContentScale.Crop, modifier = Modifier.size(150.dp)
            // 设置外部容器大小，这将是圆形的直径
            .clip(CircleShape) )
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Writer name", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { isFollowing = !isFollowing }) {
                Text(if (isFollowing) "Unfollow" else "Follow")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Book list", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        AuthorWorks()
    }
}

@Composable
fun AuthorWorks() {
    val works = listOf(
        Pair("小说一", R.drawable.dog),
        Pair("小说二", R.drawable.dog),
        Pair("小说三", R.drawable.dog)
    ) // 示例作品列表，包含作品名和封面资源ID

    LazyColumn {
        items(works) { work ->
            TextButton(onClick = { /* handle click, e.g., navigate to details */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(painter = painterResource(id = work.second), contentDescription = "作品封面", modifier = Modifier.size(80.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(work.first, textAlign = TextAlign.Left, modifier = Modifier.fillMaxWidth())
                }
            }
            Divider(color = Color.Black, thickness = 1.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAuthorDetailsPage() {
    AuthorDetailsPage()
}