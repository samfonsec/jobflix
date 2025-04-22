package br.com.jobflix.ui.main.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.jobflix.data.model.Serie
import br.com.jobflix.databinding.FragFavoritesBinding
import br.com.jobflix.util.extensions.hide
import br.com.jobflix.util.extensions.show
import br.com.jobflix.ui.details.serie.SerieDetailsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoritesViewModel by viewModel()
    private val favoritesList: ArrayList<Serie> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildUi()
        subscribeUi()
        loadFavorites()
    }

    override fun onResume() {
        super.onResume()
        loadFavorites()
    }

    private fun buildUi() {
        with(binding.rvFavorites) {
            layoutManager = LinearLayoutManager(context)
            adapter = FavoriteAdapter(
                onItemClicked = { onItemClicked(it) },
                onItemRemoved = { onRemove(it) }
            ).apply { submitList(favoritesList) }
        }
    }

    private fun subscribeUi() {
        viewModel.onFavoritesResult.observe(viewLifecycleOwner) { onFavoritesResult(it) }
        viewModel.onError.observe(viewLifecycleOwner) { onError() }
        viewModel.onLoading.observe(viewLifecycleOwner) { binding.pbLoading.isVisible = it }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onFavoritesResult(favorites: List<Serie>) {
        if (favorites.isEmpty()) {
            showEmptyState()
        } else {
            showContent()
            favoritesList += favorites
            binding.rvFavorites.adapter?.notifyDataSetChanged()
        }
    }

    private fun onError() {
        with(binding.errorView) {
            root.show()
            btTryAgain.setOnClickListener {
                loadFavorites()
                root.hide()
            }
        }
    }

    private fun showEmptyState() {
        with(binding) {
            tvEmptyState.show()
            rvFavorites.hide()
        }
    }

    private fun showContent() {
        with(binding) {
            rvFavorites.show()
            tvEmptyState.hide()
        }
    }

    private fun onItemClicked(serie: Serie) {
        context?.let { startActivity(SerieDetailsActivity.newInstance(it, serie)) }
    }

    private fun onRemove(position: Int) {
        favoritesList[position].let {
            viewModel.removeFavorite(it)
            favoritesList.remove(it)
        }
        binding.rvFavorites.adapter?.run {
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, favoritesList.size)
        }
    }

    fun loadFavorites() {
        favoritesList.clear()
        viewModel.getFavorites()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}