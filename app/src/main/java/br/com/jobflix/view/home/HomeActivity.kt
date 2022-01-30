package br.com.jobflix.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.jobflix.data.model.Serie
import br.com.jobflix.databinding.ActHomeBinding
import br.com.jobflix.util.Constants.FIRST_PAGE
import br.com.jobflix.util.EndlessRecyclerViewScrollListener
import br.com.jobflix.util.extensions.hide
import br.com.jobflix.util.extensions.show
import br.com.jobflix.util.extensions.showErrorSnackbar
import br.com.jobflix.util.extensions.viewBinding
import br.com.jobflix.view.details.DetailActivity
import br.com.jobflix.viewModel.home.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val binding by viewBinding(ActHomeBinding::inflate)

    private val viewModel: HomeViewModel by viewModel()

    private var lastLoadedPage = FIRST_PAGE

    private val gridLayoutManager by lazy { GridLayoutManager(this@HomeActivity, GRID_SPAN_COUNT) }

    private val itemsList: ArrayList<Serie> = arrayListOf()

    private val endlessScrollListener by lazy {
        object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                binding.rvSeries.removeOnScrollListener(this)
                lastLoadedPage = page
                loadSeries()
            }
        }
    }

    private val backToTopScrollListener by lazy {
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if ((recyclerView.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition() == 0) {
                    binding.fabBackToTop.hide()
                } else if (dy < 0) {
                    binding.fabBackToTop.show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        buildUi()
        subscribeUi()
        loadSeries()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavorites()
    }

    private fun buildUi() {
        setupList()
        binding.fabBackToTop.setOnClickListener { backToTop() }
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) showFirstPage()
                else searchSeries(newText)

                return true
            }
        })
    }

    private fun setupList() {
        with(binding.rvSeries) {
            layoutManager = gridLayoutManager
            adapter = SerieAdapter { onItemClicked(it) }.apply { submitList(itemsList) }
        }
        addListScrollListeners()
    }

    private fun loadSeries() {
        viewModel.loadSeries(lastLoadedPage)
    }

    private fun searchSeries(query: String) {
        resetList()
        viewModel.searchSeries(query)
    }

    private fun subscribeUi() {
        viewModel.onSeriesResult().observe(this) { onSeriesResult(it) }
        viewModel.onError().observe(this) { onError() }
        viewModel.onSearch().observe(this) { updateList(it) }
        viewModel.onLoading().observe(this) { binding.pbLoading.isVisible = it }
        viewModel.onFavoritesResult().observe(this) {

        }
        viewModel.onFavoritesError().observe(this) {

        }
    }

    private fun onSeriesResult(series: List<Serie>) {
        binding.svSearch.show()
        updateList(series)
        addListScrollListeners()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateList(series: List<Serie>) {
        itemsList += series
        binding.rvSeries.adapter?.notifyDataSetChanged()
    }

    private fun onItemClicked(serie: Serie) {
        startActivity(DetailActivity.newInstance(this, serie))
    }

    private fun onError() {
        if (lastLoadedPage == FIRST_PAGE)
            showErrorView()
        else
            showErrorSnackbar(binding.fabBackToTop) { loadSeries() }
    }

    private fun showErrorView() {
        with(binding.errorView) {
            root.show()
            btError.setOnClickListener {
                loadSeries()
                root.hide()
            }
        }
    }

    private fun backToTop() {
        showFirstPage()
        binding.rvSeries.scrollToPosition(0)
    }

    fun showFirstPage() {
        resetList()
        viewModel.getFirstPage()
    }

    private fun resetList() {
        binding.rvSeries.clearOnScrollListeners()
        endlessScrollListener.resetState()
        itemsList.clear()
        binding.fabBackToTop.hide()
    }

    private fun addListScrollListeners() {
        with(binding.rvSeries) {
            addOnScrollListener(backToTopScrollListener)
            addOnScrollListener(endlessScrollListener)
        }
    }

    companion object {
        private const val GRID_SPAN_COUNT = 3
    }
}
