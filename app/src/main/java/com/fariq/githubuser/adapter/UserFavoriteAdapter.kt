package com.fariq.githubuser.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fariq.githubuser.R
import com.fariq.githubuser.model.User
import kotlin.collections.ArrayList

class UserFavoriteAdapter : RecyclerView.Adapter<UserFavoriteAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    private var onDeleteClickCallback : OnDeleteClickCallback? = null
    private val list = ArrayList<User>()

    fun setData(items: ArrayList<User>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnDeleteClickCallback(onDeleteClickCallback: OnDeleteClickCallback) {
        this.onDeleteClickCallback = onDeleteClickCallback
    }


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user_favorite, viewGroup, false)
        return ListViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        holder.txtUserName.text = list[position].username


        Glide.with(holder.itemView.context)
                .load(list[position].avatar)
                .apply(RequestOptions().override(100, 100))
                .into(holder.imgUser)

        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(list[holder.adapterPosition])
        }
        holder.btnDelete.setOnClickListener {
            onDeleteClickCallback?.onItemClicked(list[holder.adapterPosition])
        }
        
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtUserName: TextView = itemView.findViewById(R.id.username_search_item)
        val imgUser: ImageView = itemView.findViewById(R.id.avatar_search_item)
        val btnDelete : ImageButton = itemView.findViewById(R.id.delete_item)

    }

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }

    interface OnDeleteClickCallback {
        fun onItemClicked(user: User)
    }

}