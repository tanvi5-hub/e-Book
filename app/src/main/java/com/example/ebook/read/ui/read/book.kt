package com.example.ebook.read.ui.read

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import java.io.File

@Composable
fun TxtFileContent(txtFilePath: String) {
    val fileContent = remember(txtFilePath) {
        val file = File(txtFilePath)
        file.readText()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = fileContent,
            modifier = Modifier.fillMaxWidth(),
            maxLines = Int.MAX_VALUE,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun PreviewTxtFileContent() {
    TxtFileContent("C:/Users/23227/Desktop/read/pin.txt")
}