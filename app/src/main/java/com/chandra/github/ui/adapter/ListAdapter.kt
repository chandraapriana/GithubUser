package com.chandra.github.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chandra.github.databinding.ItemRowBinding
import com.chandra.github.data.model.UserSearchData


class ListAdapter(private val listUsers: List<UserSearchData>) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {

        return ListViewHolder(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUsers[position]
        holder.binding.apply {
            tvName.text = user.login
            tvUsername.text = user.htmlUrl
            Glide.with(holder.itemView.context).load(user.avatarUrl).apply(RequestOptions().override(50,50)).into(civImage)
            root.setOnClickListener { onItemClickCallback?.onItemClicked(listUsers[position]) }

        }


    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserSearchData)
    }


}

