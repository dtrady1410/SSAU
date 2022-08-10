package com.example.weatherapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ListItemBinding

class weather_adapter: ListAdapter<ViewAdaptDataClass, weather_adapter.Holder>(Comparator()) {
    class Holder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ListItemBinding.bind(view)

        fun bind(item: ViewAdaptDataClass){
            binding.apply {
                tvDate.text = item.date
                tvTemp.text = item.temp
                tvCondidtion.text = item.condition
            }
        }
    }

    class Comparator: DiffUtil.ItemCallback<ViewAdaptDataClass>(){
        override fun areItemsTheSame(
            oldItem: ViewAdaptDataClass,
            newItem: ViewAdaptDataClass
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ViewAdaptDataClass,
            newItem: ViewAdaptDataClass
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val Item_View = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return Holder((Item_View))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}