package ru.netology.myapplication.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tarasovartem3.Delegation.Post
import com.example.tarasovartem3.R
import com.example.tarasovartem3.databinding.CardPostBinding

import kotlin.math.ln
import kotlin.math.pow
typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit
class PostsAdapter(
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener, onShareListener)
    }
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}
class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            nrav.text = post.like.toString()
            podelytsya.text = post.share.toString()
            likeimag.setImageResource(
                if (post.likedByMe) R.drawable.like_krasn else R.drawable.like_favorite_heart_5759
            )
            likeimag.setOnClickListener {
                onLikeListener(post)
            }
            nrav.text = post.like.toString()
            when {
                post.like in 1000..999999 -> nrav.text = "${post.like / 1000}K"
                post.like < 1000 -> nrav.text = post.like.toString()
                else -> nrav.text = String.format("%.1fM", post.like.toDouble() / 1000000)
            }
            podelitsya.setOnClickListener {
                onShareListener(post)
            }
            podelytsya.text = post.share.toString()
            when {
                post.share < 1000 -> podelytsya.text = post.share.toString()
                post.share in 1000..999999 -> podelytsya.text = "${post.share / 1000}K"
                else -> podelytsya.text = String.format(
                    "%.1fM", post.share.toDouble() / 1000000
                )
            }
        }
    }
}



fun getFormatedNumber(count: Long): String {
    if (count < 1000) return "" + count
    val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
    return String.format("%.1f %c", count / 1000.0.pow(exp.toDouble()), "KMGTPE"[exp - 1])
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}