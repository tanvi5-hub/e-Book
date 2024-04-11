package com.example.ebook

data class Page(
    val id: String,
    var text: String,
    val choices: MutableList<Choice>,
    var parentPageId: String? = null
)