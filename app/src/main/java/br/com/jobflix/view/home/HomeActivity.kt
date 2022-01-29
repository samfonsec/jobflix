package br.com.jobflix.view.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.jobflix.data.model.Serie
import br.com.jobflix.databinding.ActHomeBinding
import br.com.jobflix.util.Constants.FIRST_PAGE
import br.com.jobflix.util.EndlessRecyclerViewScrollListener
import br.com.jobflix.util.hide
import br.com.jobflix.util.show
import br.com.jobflix.util.viewBinding
import br.com.jobflix.view.adapters.SerieAdapter
import br.com.jobflix.view.details.DetailActivity
import br.com.jobflix.viewModel.home.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val binding by viewBinding(ActHomeBinding::inflate)

    private val viewModel: HomeViewModel by viewModel()

    private var lastLoadedPage = Int.MAX_VALUE

    private val gridLayoutManager by lazy { GridLayoutManager(this@HomeActivity, GRID_SPAN_COUNT) }

    private val itemsList: ArrayList<Serie> = arrayListOf()

    private val endlessScrollListener by lazy {
        object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                binding.rvSeries.removeOnScrollListener(this)
                lastLoadedPage = page
                loadSeries(page)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        buildUi()
        subscribeUi()
        loadSeries(FIRST_PAGE)
    }

    private fun buildUi() {
        setupList()
        binding.fabBackToTop.setOnClickListener { backToTop() }
    }

    private fun setupList() {
        val backToTopScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if ((recyclerView.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition() == 0) {
                    binding.fabBackToTop.hide()
                } else if (dy < 0) {
                    binding.fabBackToTop.show()
                }
            }
        }
        with(binding.rvSeries) {
            layoutManager = gridLayoutManager
            adapter = SerieAdapter { onItemClicked(it) }.apply { submitList(itemsList) }
            addOnScrollListener(endlessScrollListener)
            addOnScrollListener(backToTopScrollListener)
        }
    }

    private fun loadSeries(page: Int) {
        viewModel.loadSeries(page)
    }

    private fun subscribeUi() {
        viewModel.onSeriesResult().observe(this) { onSeriesResult(it) }
        viewModel.onError().observe(this) { onError() }
        viewModel.onLoading().observe(this) { isLoading ->
            if (isLoading) showLoading()
            else hideLoading()
        }
    }

    private fun onSeriesResult(series: List<Serie>) {
        itemsList += series
        val positionStart = itemsList.size - series.size
        with(binding.rvSeries) {
            adapter?.notifyItemRangeInserted(positionStart, itemsList.size - 1)
            addOnScrollListener(endlessScrollListener)
        }
    }

    private fun onItemClicked(serie: Serie) {
        startActivity(DetailActivity.newInstance(this, serie))
    }

    private fun onError() {
        Snackbar.make(binding.root, "Error!", Snackbar.LENGTH_SHORT).show() // TODO
    }

    private fun backToTop() {
        with(binding) {
            rvSeries.scrollToPosition(0)
            fabBackToTop.hide()
        }
    }

    private fun resetList() {
        binding.rvSeries.removeOnScrollListener(endlessScrollListener)
        endlessScrollListener.resetState()
        itemsList.clear()
        binding.rvSeries.addOnScrollListener(endlessScrollListener)
    }

    private fun showLoading() = binding.pbLoading.show()

    private fun hideLoading() = binding.pbLoading.hide()

    companion object {
        private const val GRID_SPAN_COUNT = 3
    }
}