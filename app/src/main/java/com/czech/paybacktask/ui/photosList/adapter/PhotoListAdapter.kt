package com.czech.paybacktask.ui.photosList.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.czech.paybacktask.R
import com.czech.paybacktask.data.network.model.Result

class PhotoListAdapter(diffCallback: PhotoListDiffCallback) :
    ListAdapter<Result.Hit, PhotoListAdapter.PhotoListViewHolder>(diffCallback) {

    class PhotoListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val userName: TextView = itemView.findViewById(R.id.user_name)
        private val photo: ImageView = itemView.findViewById(R.id.photo)
        private val tags: TextView = itemView.findViewById(R.id.tags)

        @SuppressLint("SetTextI18n")
        fun bind(data: Result.Hit) {
            userName.text = data.user
            tags.text = data.tags

            Glide.with(itemView)
                .load(data.previewURL)
                .into(photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.photos_list_item, parent, false)

        return PhotoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }
}

object PhotoListDiffCallback : DiffUtil.ItemCallback<Result.Hit>() {
    override fun areItemsTheSame(oldItem: Result.Hit, newItem: Result.Hit): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Result.Hit,
        newItem: Result.Hit
    ): Boolean {
        return oldItem.id == newItem.id
    }
}