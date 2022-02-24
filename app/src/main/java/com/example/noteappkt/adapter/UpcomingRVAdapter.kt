package com.example.noteappkt.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappkt.R
import com.example.noteappkt.databinding.UpcomingRvItemBinding
import com.example.noteappkt.room.entities.NoteEntity

class UpcomingRVAdapter(private val data: ArrayList<NoteEntity>,private var listener: CardClickListener):RecyclerView.Adapter<UpcomingRVAdapter.UpcomingRvViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingRvViewHolder {
        val binding:UpcomingRvItemBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.upcoming_rv_item,parent,false)
        return UpcomingRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpcomingRvViewHolder, position: Int) {

        holder.bind(data[position],listener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class UpcomingRvViewHolder(private val binding:UpcomingRvItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(noteEntity: NoteEntity, listener: CardClickListener){

            binding.upcomingCard.setCardBackgroundColor(Color.parseColor(noteEntity.noteModel.color))
            binding.pinnedtitle.text = noteEntity.noteModel.title
            binding.pinneddescription.text = noteEntity.noteModel.description
            binding.upcomingCard.setOnClickListener {
                listener.onItemClickListener(noteEntity)
            }

            binding.imageFilterButton2.setOnClickListener {
                listener.onMenuItemClickListener(it,noteEntity)
            }
            binding.executePendingBindings()
        }
    }
}