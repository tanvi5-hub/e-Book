package com.example.ebook.read.model

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.values
import com.google.firebase.database.values
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StoriesViewModel : ViewModel() {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val storiesLiveData = MutableLiveData<List<Book>>()
    private val bookDetailsLiveData = MutableLiveData<Book>()
    private val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private val userId: String = currentUser?.uid ?: throw IllegalStateException("User not logged in")
    private val _stories = MutableStateFlow<List<Book>>(emptyList())
    val stories: StateFlow<List<Book>> = _stories.asStateFlow()

    private val _filter = MutableStateFlow("all")
    val filter: StateFlow<String> = _filter.asStateFlow()
    init {
        observeStories()
        fetchStories()
    }
    private fun fetchStories() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("stories")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val books = mutableListOf<Book>()
                snapshot.children.forEach { bookSnapshot ->
                    val book = bookSnapshot.getValue(Book::class.java)
                    book?.let { books.add(it) }
                }
                _stories.value = books
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("StoriesViewModel", "Failed to read value.", error.toException())
            }
        })
    }

    fun setFilter(filter: String) {
        _filter.value = filter
    }

    val filteredStories = filter.combine(_stories) { filter, stories ->
        if (filter == "all") {
            stories
        } else {
            stories.filter { it.category == filter }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    private fun observeStories() {
        val booksRef = database.getReference("stories")
        booksRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val books = mutableListOf<Book>()
                snapshot.children.forEach { childSnapshot ->
                    val book = childSnapshot.getValue(Book::class.java)?.apply {
                        bookid = childSnapshot.key ?: ""
                    }
                    book?.let { books.add(it) }
                }
                storiesLiveData.postValue(books)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("StoriesViewModel", "Failed to read stories: ${error.message}")
            }
        })
    }
    fun getBookDetails(bookId: String): LiveData<Book> {
        viewModelScope.launch {
            fetchBookDetails(bookId)
        }
        return bookDetailsLiveData
    }

    @SuppressLint("NullSafeMutableLiveData")
    private suspend fun fetchBookDetails(bookId: String) = withContext(Dispatchers.IO) {
        val bookRef = database.getReference("stories").child(bookId)
        try {
            val snapshot = bookRef.get().await()
            val book = snapshot.getValue(Book::class.java)?.apply {
                bookid = snapshot.key ?: ""
            }
            book?.let {
                it.authorName = fetchAuthorName(it.authorId)?: "Unknown Author"
                bookDetailsLiveData.postValue(it)
                Log.d("StoriesViewModel", "Book loaded: ID=${it.bookid}, Author=${it.authorName}, Name=${it.name}")
            }
        } catch (e: Exception) {
            Log.e("StoriesViewModel", "Error fetching book details for ID $bookId: ${e.message}", e)
            bookDetailsLiveData.postValue(null)
        }
    }


    fun getStories(): LiveData<List<Book>> {
        viewModelScope.launch {
            fetchStoriesDetails()
        }
        return storiesLiveData
    }
    fun getBooks(bookIds: List<String>): LiveData<List<Book>> {
        viewModelScope.launch {
            fetchBooksDetails(bookIds)
        }
        return storiesLiveData
    }
   //这里
    private suspend fun fetchStoriesDetails() = withContext(Dispatchers.IO) {
        val books = mutableListOf<Book>()
        val booksRef = database.getReference("stories")

        try {
            val snapshot = booksRef.get().await()
            snapshot.children.forEach { childSnapshot ->
                val book = childSnapshot.getValue(Book::class.java)?.apply {
                    bookid = childSnapshot.key ?: ""  // 确保bookid是从Firebase的键中设置的
                }
                if (book != null) {
                    books.add(book)
                    Log.d("StoriesViewModel", "Book loaded: ID=${book.bookid}, Name=${book.name}")
                } else {
                    Log.d("StoriesViewModel", "Failed to load book with ID: ${childSnapshot.key}")
                }
            }
            storiesLiveData.postValue(books)
        } catch (e: Exception) {
            Log.e("StoriesViewModel", "Error fetching books: ${e.message}", e)
            storiesLiveData.postValue(emptyList())
        }
    }
    private suspend fun fetchBooksDetails(storyIds: List<String>) = withContext(Dispatchers.IO) {
        val books = mutableListOf<Book>()
        val booksRef = database.getReference("stories")

        try {
            val snapshot = booksRef.get().await()
            val deferreds = snapshot.children.mapNotNull { childSnapshot ->
                async {
                    val book = childSnapshot.getValue(Book::class.java)?.apply {
                        bookid = childSnapshot.key ?: ""  // 从Firebase的键中设置bookid
                    }
                    // 只处理那些ID存在于storyIds列表中的书籍
                    if (book != null && storyIds.contains(book.bookid)) {
                        book.authorName = fetchAuthorName(book.authorId)!!
                        Log.d("BooksViewModel", "Book loaded: ID=${book.bookid}, Name=${book.name}")
                        book
                    } else {
                        null
                    }
                }
            }
            // 等待所有异步操作完成
            deferreds.forEach { deferred ->
                deferred.await()?.let { book ->
                    books.add(book)
                }
            }
            storiesLiveData.postValue(books)  // 更新LiveData，只包含查询到的书籍
        } catch (e: Exception) {
            Log.e("StoriesViewModel", "Error fetching books: ${e.message}", e)
            storiesLiveData.postValue(emptyList())  // 发生错误时，更新LiveData为一个空列表
        }
    }
    private suspend fun fetchAuthorName(authorId: String): String? = withContext(Dispatchers.IO) {
        Log.e("SureR", "No author name found for ID: $authorId")
        val authorRef = database.getReference("user").child(authorId).child("name")
        try {
            val snapshot = authorRef.get().await()

            val authorName = snapshot.value as? String
            if (authorName == null) {
                Log.e("StoriesViewModel", "No author name found for ID: $authorId")
            } else {
                Log.d("StoriesViewModel", "Fetched author name: $authorName")
            }
            return@withContext authorName
        } catch (e: Exception) {
            Log.e("StoriesViewModel", "Error fetching author name: ${e.message}", e)
            return@withContext null
        }
    }



    fun updateUserReadingPosition(userId: String, bookId: String, pageId: String) {
        val userPageRef = database.getReference("user/$userId/booklist/$bookId")
        userPageRef.setValue(pageId).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("ViewModel", "User reading position updated successfully.")
            } else {
                Log.e("ViewModel", "Failed to update user reading position.", it.exception)
            }
        }
    }
    @SuppressLint("NullSafeMutableLiveData")
    fun getUserReadingPosition(userId: String, bookId: String): LiveData<String> {
        val currentPageIdLiveData = MutableLiveData<String>()
        val userPageRef = database.getReference("user/$userId/booklist/$bookId")
        userPageRef.get().addOnSuccessListener { snapshot ->
            val currentPageId = snapshot.getValue(String::class.java)
            currentPageId?.let {
                currentPageIdLiveData.postValue(it)
            }
        }.addOnFailureListener {
            Log.e("ViewModel", "Failed to fetch user reading position.", it)
            currentPageIdLiveData.postValue(null)
        }
        return currentPageIdLiveData
    }
    fun updateUserReadingPosition(bookId: String, pageId: String) {
        if (userId == null) return  // 确保用户已登录

        val userPageRef = database.getReference("user/$userId/booklist/$bookId")
        userPageRef.setValue(pageId).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("ViewModel", "User reading position updated successfully.")
            } else {
                Log.e("ViewModel", "Failed to update user reading position.", it.exception)
            }
        }
    }
    @SuppressLint("NullSafeMutableLiveData")
    fun getUserReadingPosition(bookId: String): LiveData<String> {
        val currentPageIdLiveData = MutableLiveData<String>()
        if (userId == null) {
            currentPageIdLiveData.postValue(null)
            return currentPageIdLiveData  // 确保用户已登录
        }

        val userPageRef = database.getReference("user/$userId/booklist/$bookId")
        userPageRef.get().addOnSuccessListener { snapshot ->
            val currentPageId = snapshot.getValue(String::class.java)
            currentPageId?.let {
                currentPageIdLiveData.postValue(it)
            }
        }.addOnFailureListener {
            Log.e("ViewModel", "Failed to fetch user reading position.", it)
            currentPageIdLiveData.postValue(null)
        }
        return currentPageIdLiveData
    }
}

data class Book(
    var bookid: String = "",
    val name: String = "",
    var authorId: String = "",
    var authorName: String = "",
    val description: String = "",
    val imageUrl: String = "",
    var pages: Map<String, Page> = mapOf(),  // 确保pages是可变的，以允许设置pageid
    val category: String = ""
)

data class Page(
    var pageid: String = "",
    var text: String = "",
    var choices: Map<String, String> = mapOf(),  // 存储 choice 文本和 destination 页面 ID 的映射
)

data class Choice(
    var text: String = "",
    var destinationPageId: String = ""
)
