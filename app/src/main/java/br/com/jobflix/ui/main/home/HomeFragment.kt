package br.com.jobflix.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.jobflix.data.model.Serie
import br.com.jobflix.databinding.FragHomeBinding
import br.com.jobflix.util.Constants.FIRST_PAGE
import br.com.jobflix.util.EndlessRecyclerViewScrollListener
import br.com.jobflix.util.extensions.hide
import br.com.jobflix.util.extensions.show
import br.com.jobflix.util.extensions.showErrorSnackbar
import br.com.jobflix.ui.auth.HandlePinDialog
import br.com.jobflix.ui.details.serie.SerieDetailsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel()
    private var lastLoadedPage = FIRST_PAGE
    private val gridLayoutManager by lazy { GridLayoutManager(context, GRID_SPAN_COUNT) }
    private val seriesList: ArrayList<Serie> = arrayListOf()
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
    private val onQueryTextListener by lazy {
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.cancelLastJob()
                    showFirstPage()
                } else searchSeries(newText)

                return true
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildUi()
        subscribeUi()
        loadSeries()
    }

    private fun buildUi() {
        setupList()
        with(binding) {
            fabBackToTop.setOnClickListener { showFirstPage() }
            svSearch.setOnQueryTextListener(onQueryTextListener)
            ivSecurity.setOnClickListener { onSecurityButtonClicked() }
        }
    }

    private fun subscribeUi() {
        viewModel.onSeriesResult.observe(viewLifecycleOwner) { onSeriesResult(it) }
        viewModel.onError.observe(viewLifecycleOwner) { onError(it) }
        viewModel.onSearch.observe(viewLifecycleOwner) { onSearch(it) }
        viewModel.onLoading.observe(viewLifecycleOwner) { binding.pbLoading.isVisible = it }
    }

    private fun loadSeries() {
        viewModel.loadSeries(lastLoadedPage)
    }

    private fun setupList() {
        with(binding.rvSeries) {
            layoutManager = gridLayoutManager
            adapter = SerieAdapter { onItemClicked(it) }.apply { submitList(seriesList) }
        }
        addListScrollListeners()
    }

    private fun onSeriesResult(series: List<Serie>) {
        binding.svSearch.show()
        binding.ivSecurity.show()
        updateList(series)
        addListScrollListeners()
    }

    private fun onError(isCanceling: Boolean) {
        if (isCanceling)
            return

        if (lastLoadedPage == FIRST_PAGE)
            showErrorView()
        else
            activity?.showErrorSnackbar(binding.fabBackToTop) { loadSeries() }
    }

    private fun onSearch(series: List<Serie>) {
        resetList()
        updateList(series)

        if (series.isEmpty())
            binding.tvEmptyState.show()
    }

    private fun searchSeries(query: String) {
        binding.tvEmptyState.hide()
        viewModel.searchSeries(query)
    }

    private fun updateList(series: List<Serie>) {
        val previousContentSize = seriesList.size
        seriesList += series
        binding.rvSeries.adapter?.notifyItemRangeInserted(previousContentSize, seriesList.size)
    }

    private fun onItemClicked(serie: Serie) {
        context?.let { startActivity(SerieDetailsActivity.newInstance(it, serie)) }
    }

    private fun showErrorView() {
        with(binding.errorView) {
            root.show()
            btTryAgain.setOnClickListener {
                loadSeries()
                root.hide()
            }
        }
    }

    private fun showFirstPage() {
        resetList()
        viewModel.getFirstPage()
    }

    private fun resetList() {
        binding.tvEmptyState.hide()
        val previousListSize = seriesList.size
        seriesList.clear()
        endlessScrollListener.resetState()
        with(binding) {
            fabBackToTop.hide()
            rvSeries.clearOnScrollListeners()
            rvSeries.adapter?.notifyItemRangeRemoved(0, previousListSize)
        }
    }

    private fun addListScrollListeners() {
        with(binding.rvSeries) {
            addOnScrollListener(backToTopScrollListener)
            addOnScrollListener(endlessScrollListener)
        }
    }

    private fun onSecurityButtonClicked() {
        if (viewModel.hasPin())
            showRemovePinDialog()
        else
            showCreatePinDialog()
    }

    private fun showCreatePinDialog() {
        HandlePinDialog.newInstance(false).apply {
            onSavePin { pin, enableFingerprint ->
                viewModel.savePin(pin)
                viewModel.enableFingerprint(enableFingerprint)
                dismiss()
            }
        }.show(childFragmentManager, TAG)
    }

    private fun showRemovePinDialog() {
        HandlePinDialog.newInstance(true).apply {
            onRemovePin {
                if (viewModel.isValidPin(it)) {
                    viewModel.removePin()
                    dismiss()
                } else {
                    setError()
                }
            }
        }.show(childFragmentManager, TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        private const val TAG = "HomeFragment"
        private const val GRID_SPAN_COUNT = 3
    }
}
