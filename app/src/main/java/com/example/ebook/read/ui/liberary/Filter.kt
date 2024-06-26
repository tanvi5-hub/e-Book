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
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRow
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
import com.example.ebook.read.model.StoriesViewModel
import com.example.ebook.read.ui.theme.ReaderTheme
import com.example.ebook.read.ui.liberary.FilterButton
import com.google.accompanist.flowlayout.FlowRow
@Composable
fun FilterButton(navController: NavHostController, storiesViewModel: StoriesViewModel) {
    var showMenu by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        BottomAppBar {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = { showMenu = !showMenu },
                    modifier = Modifier.width(150.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.FilterAlt,
                        contentDescription = "Filter books",
                        tint = Color.Black
                    )
                    Text(text = "Filter books", color = Color.Black)
                }
            }
        }

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
                    DropdownContent(navController, storiesViewModel)
                }
            }
        }
    }
}


@Composable
fun DropdownContent(navController: NavHostController, storiesViewModel: StoriesViewModel) {
    FlowRow(
        mainAxisSpacing = 16.dp,
        crossAxisSpacing = 8.dp,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        DropdownButton("fantasy", "fantasy",  storiesViewModel)

        DropdownButton("Filter Option 2", "filter_option_2", storiesViewModel)
        DropdownButton("Filter Option 3", "filter_option_3",  storiesViewModel)
        DropdownButton("all", "all",  storiesViewModel)
    }
}

@Composable
fun DropdownButton(text: String, filterValue: String,  storiesViewModel: StoriesViewModel) {
    Button(
        onClick = {
            storiesViewModel.setFilter(filterValue)
            var showMenu = false  // Ensure this logic runs correctly
        },
        modifier = Modifier.padding(horizontal = 0.dp)
    ) {
        Text(text)
    }
}


