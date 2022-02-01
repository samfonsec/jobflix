package br.com.jobflix.view.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.jobflix.data.model.Serie
import br.com.jobflix.databinding.ItemSerieBinding

class SerieAdapter(
    private val onItemClicked: (Serie) -> Unit
) : ListAdapter<Serie, SerieAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSerieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { serie ->
            holder.bind(serie)
            holder.itemView.setOnClickListener { onItemClicked(serie) }
        }
    }

    class ViewHolder(private val binding: ItemSerieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(serie: Serie) {
            with(binding) {
                ivPoster.setImageURI(serie.image?.medium)
                tvSerieName.text = serie.name
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Serie> =
            object : DiffUtil.ItemCallback<Serie>() {
                override fun areItemsTheSame(oldItem: Serie, newItem: Serie): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Serie, newItem: Serie): Boolean {
                    return oldItem == newItem
                }
            }
    }
}