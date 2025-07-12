package com.example.magangberdampak2025_suitmedia.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.magangberdampak2025_suitmedia.R
import com.example.magangberdampak2025_suitmedia.model.User

class UserAdapter(
    private var userList: List<User>,
    private val onItemClick: (User) -> Unit
): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFirstname: TextView = itemView.findViewById(R.id.tvFirstname)
        val tvLastname: TextView = itemView.findViewById(R.id.tvLastname)
        val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        val ivAvater: ImageView = itemView.findViewById(R.id.ivAvatar)
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(userList[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val rowUsers = LayoutInflater.from(parent.context).inflate(R.layout.row_users, parent, false)
        return UserViewHolder(rowUsers)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.tvFirstname.text = user.firstName
        holder.tvLastname.text = user.lastName
        holder.tvEmail.text = user.email
        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .into(holder.ivAvater)
    }

    override fun getItemCount(): Int = userList.size

    fun setData(newList: List<User>) {
        userList = newList
        notifyDataSetChanged()
    }
}