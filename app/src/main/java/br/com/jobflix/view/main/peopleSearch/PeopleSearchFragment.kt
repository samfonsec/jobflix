package br.com.jobflix.view.main.peopleSearch

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import br.com.jobflix.data.model.People
import br.com.jobflix.databinding.FragPeopleSearchBinding
import br.com.jobflix.util.extensions.hide
import br.com.jobflix.util.extensions.show
import br.com.jobflix.util.extensions.showSoftKeyboard
import br.com.jobflix.util.extensions.viewBinding
import br.com.jobflix.view.base.BaseFragment
import br.com.jobflix.view.details.PeopleDetailActivity
import br.com.jobflix.viewModel.main.PeopleSearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class PeopleSearchFragment : BaseFragment() {

    override val binding by viewBinding(FragPeopleSearchBinding::inflate)

    private val viewModel: PeopleSearchViewModel by viewModel()

    private val peopleList: ArrayList<People> = arrayListOf()

    private val onQueryTextListener by lazy {
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) clearList()
                else searchPeople(newText)

                return true
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildUi()
        subscribeUi()
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (menuVisible)
            binding.svSearch.requestFocus()
    }

    private fun buildUi() {
        setupList()
        binding.svSearch.setOnQueryTextListener(onQueryTextListener)
    }

    private fun setupList() {
        with(binding.rvPeople) {
            layoutManager = GridLayoutManager(context, GRID_SPAN_COUNT)
            adapter = PeopleAdapter { onItemClicked(it) }.apply { submitList(peopleList) }
        }
    }

    private fun onItemClicked(people: People) {
        context?.let { startActivity(PeopleDetailActivity.newInstance(it, people)) }
    }

    private fun subscribeUi() {
        viewModel.onSearch().observe(viewLifecycleOwner) { onSearchResult(it) }
        viewModel.onError().observe(viewLifecycleOwner) { showEmptyState() }
    }

    private fun onSearchResult(people: List<People>) {
        if (people.isEmpty()) {
            showEmptyState()
        } else {
            val previousContentSize = peopleList.size
            clearList()
            peopleList += people
            binding.rvPeople.adapter?.notifyItemRangeInserted(previousContentSize, peopleList.size)
            showContent()
        }
    }

    private fun searchPeople(name: String) {
        viewModel.searchForPeople(name)
        binding.tvEmptyState.hide()
    }

    private fun clearList() {
        val previousListSize = peopleList.size
        peopleList.clear()
        binding.rvPeople.adapter?.notifyItemRangeRemoved(0, previousListSize)
    }

    private fun showEmptyState() {
        with(binding) {
            tvEmptyState.show()
            rvPeople.hide()
        }
    }

    private fun showContent() {
        with(binding) {
            rvPeople.show()
            tvEmptyState.hide()
        }
    }

    companion object {
        private const val GRID_SPAN_COUNT = 3
    }
}