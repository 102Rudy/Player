package com.rygital.player.presentation.explorer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rygital.player.R
import com.rygital.player.databinding.ItemAudioBinding
import com.rygital.player.domain.AudioFile

class ExplorerAdapter : RecyclerView.Adapter<ExplorerAdapter.AudioHolder>() {

    private val items: MutableList<AudioFile> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemAudioBinding>(layoutInflater, R.layout.item_audio, parent, false)
        return AudioHolder(binding)
    }

    override fun onBindViewHolder(holder: AudioHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<AudioFile>) {
        this.items.clear()
        this.items.addAll(items)

        notifyDataSetChanged()
    }

    class AudioHolder(private val binding: ItemAudioBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AudioFile) {
            binding.audioFile = item
            binding.executePendingBindings()
        }
    }
}