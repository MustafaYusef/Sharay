package com.mustafayusef.sharay.data.utils

import androidx.recyclerview.widget.DiffUtil
import com.mustafayusef.sharay.data.models.DataCars

class PostsDiffUtilCallback(private val oldList: List<DataCars>, private val newList: List<DataCars>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = true // for the sake of simplicity we return true here but it can be changed to reflect a fine-grained control over which part of our views are updated
}