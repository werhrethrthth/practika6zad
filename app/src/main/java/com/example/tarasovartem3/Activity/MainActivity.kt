package com.example.tarasovartem3.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import com.example.tarasovartem3.Viewmodel.PostViewModel
import com.example.tarasovartem3.databinding.ActivityMainBinding

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import ru.netology.myapplication.Adapter.PostsAdapter

//import kotlin.android.synthetic.main.activity_main.*

class MainActivity<TextView> : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter({
            viewModel.likeById(it.id)},
            {
                viewModel.shareById(it.id)
            })
        binding.list.adapter=adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
    }
}

    @MainThread
    public inline fun <reified VM : ViewModel> ComponentActivity.viewModels(
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
    ): Lazy<VM> {
        val factoryPromise = factoryProducer ?: {
            defaultViewModelProviderFactory
        }

        return ViewModelLazy(
            VM::class,
            { viewModelStore },
            factoryPromise,
            { this.defaultViewModelCreationExtras }
        )
    }





