package com.picpay.desafio.android.usersFeature.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.usersFeature.data.UserVO
import com.picpay.desafio.android.databinding.ListItemUserBinding

class UserListAdapter : RecyclerView.Adapter<UserListItemViewHolder>() {

    var usersVO = emptyList<UserVO>()
        set(value) {
            val result = DiffUtil.calculateDiff(
                UserListDiffCallback(
                    field,
                    value
                )
            )
            result.dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        val binding =
            ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return UserListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(usersVO[position])
    }

    override fun getItemCount(): Int = usersVO.size
}
