package com.rygital.player.explorer.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rygital.core.model.AudioFile
import com.rygital.player.explorer.R
import com.rygital.player.explorer.databinding.ItemAudioBinding


internal class ExplorerAdapter(
        private val itemClickListener: (AudioFile) -> Unit
) : RecyclerView.Adapter<ExplorerAdapter.AudioHolder>() {

    private val items: MutableList<AudioFile> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemAudioBinding>(layoutInflater, R.layout.item_audio, parent, false)
        return AudioHolder(binding).apply {
            setClickListener(itemClickListener)
        }
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

        fun setClickListener(click: (AudioFile) -> Unit) {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    binding.audioFile?.let {
                        click(it)
                    }
                }
            }
        }

        fun bind(item: AudioFile) {
            binding.audioFile = item
            binding.executePendingBindings()
        }
    }
}