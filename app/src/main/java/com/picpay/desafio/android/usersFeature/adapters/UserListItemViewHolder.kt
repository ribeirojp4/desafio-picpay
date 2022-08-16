package com.picpay.desafio.android.usersFeature.adapters

import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.usersFeature.data.UserVO
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.gone
import com.picpay.desafio.android.loadFromPicasso
import com.picpay.desafio.android.visible

class UserListItemViewHolder(
    private val itemViewBinding: ListItemUserBinding
) : RecyclerView.ViewHolder(itemViewBinding.root) {

    fun bind(userVO: UserVO) {
        with(itemViewBinding) {
            progressBar.visible()
            name.text = userVO.name
            username.text = userVO.username
            picture.loadFromPicasso(
                userVO.img,
                R.drawable.ic_round_account_circle,
                ::onLoadImageSuccess,
                ::onLoadImageError
            )
        }
    }

    private fun onLoadImageSuccess() {
        itemViewBinding.progressBar.gone()
    }

    private fun onLoadImageError() {
        itemViewBinding.progressBar.gone()
    }
}
