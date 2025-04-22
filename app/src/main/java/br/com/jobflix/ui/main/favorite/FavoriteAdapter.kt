package br.com.jobflix.ui.main.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.jobflix.data.model.Serie
import br.com.jobflix.databinding.ItemFavoriteBinding

class FavoriteAdapter(
    private val onItemClicked: (Serie) -> Unit,
    private val onItemRemoved: (Int) -> Unit,
) : ListAdapter<Serie, FavoriteAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { serie ->
            holder.bind(serie) { onItemRemoved(position) }
            holder.itemView.setOnClickListener { onItemClicked(serie) }
        }
    }

    class ViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(serie: Serie, onRemoved: View.OnClickListener) {
            with(binding) {
                tvName.text = serie.name
                tvGenres.text = serie.formattedGenres()
                ivPoster.setImageURI(serie.image?.original)
                tvYear.text = serie.premiereYear()
                serie.rating?.average?.let { tvRating.text = it.toString() }
                ivDelete.setOnClickListener(onRemoved)
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