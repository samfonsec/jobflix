package br.com.jobflix.view.main.peopleSearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.jobflix.R
import br.com.jobflix.data.model.People
import br.com.jobflix.databinding.ItemPeopleBinding
import br.com.jobflix.util.extensions.loadImageFromUrl

class PeopleAdapter(
    private val onItemClicked: (People) -> Unit
) : ListAdapter<People, PeopleAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPeopleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { people ->
            holder.bind(people)
            holder.itemView.setOnClickListener { onItemClicked(people) }
        }
    }

    class ViewHolder(private val binding: ItemPeopleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(people: People) {
            with(binding) {
                ivPeople.loadImageFromUrl(people.image?.medium, R.drawable.bg_placeholder_people)
                tvPeopleName.text = people.name
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<People> =
            object : DiffUtil.ItemCallback<People>() {
                override fun areItemsTheSame(oldItem: People, newItem: People): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: People, newItem: People): Boolean {
                    return oldItem == newItem
                }
            }
    }
}