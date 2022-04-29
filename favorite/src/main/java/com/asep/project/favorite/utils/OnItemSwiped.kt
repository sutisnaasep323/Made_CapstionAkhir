package com.asep.project.favorite.utils

import androidx.recyclerview.widget.RecyclerView

interface OnItemSwiped {
    fun onSwiped(viewHolder: RecyclerView.ViewHolder)
}