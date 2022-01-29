package br.com.jobflix.view.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.jobflix.data.model.Episode
import br.com.jobflix.databinding.ItemEpisodeBinding
import br.com.jobflix.util.Constants.EPISODE_NUMBER_NAME_FORMAT
import br.com.jobflix.util.extensions.loadImageFromUrl

class EpisodeAdapter(
    private val onItemClicked: (Episode) -> Unit
) : ListAdapter<Episode, EpisodeAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { serie ->
            holder.bind(serie)
            holder.itemView.setOnClickListener { onItemClicked(serie) }
        }
    }

    class ViewHolder(private val binding: ItemEpisodeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: Episode) {
            with(binding) {
                ivPoster.loadImageFromUrl(episode.image?.original)
                tvEpNumber.text = String.format(EPISODE_NUMBER_NAME_FORMAT, episode.number, episode.name)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Episode> =
            object : DiffUtil.ItemCallback<Episode>() {
                override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                    return oldItem == newItem
                }
            }
    }
}