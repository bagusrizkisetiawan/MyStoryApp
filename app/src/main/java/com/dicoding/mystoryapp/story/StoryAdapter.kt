package com.dicoding.mystoryapp.story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mystoryapp.api.ListStoryItem
import com.dicoding.mystoryapp.databinding.ItemStoryBinding

class StoryAdapter(private val stories: List<ListStoryItem>) : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {
    // Membuat view holder baru
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        // Menggunakan ItemStoryBinding untuk menghubungkan layout item cerita dengan adapter
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    // Menghubungkan data cerita ke tampilan
    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        // Mengambil objek cerita dari daftar berdasarkan posisi
        val story = stories[position]
        // Memanggil fungsi bind pada view holder untuk mengisi data cerita ke tampilan
        holder.bind(story)
    }

    // Mengembalikan jumlah item dalam daftar cerita
    override fun getItemCount(): Int {
        return stories.size
    }

    // ViewHolder untuk tampilan cerita
    class StoryViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        // Fungsi untuk mengisi data cerita ke tampilan menggunakan binding
        fun bind(story: ListStoryItem) {
            binding.tvStoryName.text = story.name
            binding.tvStoryDesc.text = story.description

            // Menggunakan Glide untuk memuat gambar cerita dari URL ke ImageView dalam layout item cerita
            Glide.with(binding.root)
                .load(story.photoUrl)
                .into(binding.imgStory)
        }
    }
}