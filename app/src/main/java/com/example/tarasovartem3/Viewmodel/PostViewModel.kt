package com.example.tarasovartem3.Viewmodel

import androidx.lifecycle.ViewModel
//import com.example.tarasovartem3.PostRepository
//import com.example.tarasovartem3.PostRepositoryInMemoryImpl
import com.example.tarasovartem3.Repository.PostRepository
import com.example.tarasovartem3.Repository.PostRepositoryInMemoryImpl


class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    fun likeById(id: Int)=repository.likeById(id)
    fun shareById(id: Int)=repository.shareById(id)
}