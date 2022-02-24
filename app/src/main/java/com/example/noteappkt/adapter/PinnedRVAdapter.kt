package com.example.noteappkt.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.PrecomputedTextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappkt.R
import com.example.noteappkt.databinding.PinnedRvItemBinding
import com.example.noteappkt.room.entities.NoteEntity

class PinnedRVAdapter(private val data: ArrayList<NoteEntity>,private var listener: CardClickListener) :
    RecyclerView.Adapter<PinnedRVAdapter.PinnedRVViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinnedRVViewHolder {
        val binding: PinnedRvItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.pinned_rv_item, parent, false
        )
        return PinnedRVViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PinnedRVViewHolder, position: Int) {

        holder.bind(data[position],listener)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    class PinnedRVViewHolder(private val binding: PinnedRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(noteEntity: NoteEntity, listener: CardClickListener) {
            binding.pinnedtitle.setTextFuture(
                PrecomputedTextCompat.getTextFuture(
                    noteEntity.noteModel.title,
                    binding.pinnedtitle.textMetricsParamsCompat,
                    null
                )
            )

            binding.pinneddescription.text = noteEntity.noteModel.description
            binding.pinnedcardview.setCardBackgroundColor(Color.parseColor(noteEntity.noteModel.color))
            binding.pinnedcardview.setOnClickListener {
                listener.onItemClickListener(noteEntity)
            }

            binding.imageFilterButton.setOnClickListener {
                listener.onMenuItemClickListener(it,noteEntity)
            }

            binding.executePendingBindings()
        }
    }
}