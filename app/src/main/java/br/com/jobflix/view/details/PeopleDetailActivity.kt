package br.com.jobflix.view.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import br.com.jobflix.R
import br.com.jobflix.data.model.People
import br.com.jobflix.data.model.Serie
import br.com.jobflix.databinding.ActPeopleDetailsBinding
import br.com.jobflix.util.extensions.*
import br.com.jobflix.view.main.home.SerieAdapter
import br.com.jobflix.viewModel.details.PeopleDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class PeopleDetailActivity : AppCompatActivity() {

    private val binding by viewBinding(ActPeopleDetailsBinding::inflate)

    private val viewModel: PeopleDetailsViewModel by viewModel()

    private val people by extra<People>(ARG_PEOPLE)

    private val seriesList: ArrayList<Serie> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        buildUi()
        subscribeUi()
        loadPeopleSeries()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else super.onOptionsItemSelected(item)
    }


    private fun buildUi() {
        setupActionBar()
        setupList()
        with(binding) {
            ivPeople.setImageURI(people.image?.original)
            tvName.text = people.name
            people.birthday?.toDate()?.let {
                tvBirthdate.text = if (people.deathday == null)
                    getString(R.string.birthdate_age_format, it.format(), it.age())
                else
                    it.format()
            }
            people.country?.name?.let {
                tvCountry.text = it
            }
            people.deathday?.toDate()?.let {
                tvDeathDate.text = getString(R.string.death_date_format, it.format())
            }
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
    }

    private fun setupList() {
        with(binding.rvSeries) {
            layoutManager = GridLayoutManager(this@PeopleDetailActivity, 3)
            adapter = SerieAdapter { onItemClicked(it) }.apply {
                submitList(seriesList)
            }
        }
    }

    private fun subscribeUi() {
        viewModel.onSeriesResult().observe(this) { onPeopleSeriesResult(it) }
        viewModel.onError().observe(this) { showErrorView() }
        viewModel.onLoading().observe(this) { binding.pbLoadingSeries.isVisible = it }
    }

    private fun onPeopleSeriesResult(series: List<Serie>) {
        seriesList += series
        binding.rvSeries.adapter?.notifyItemRangeInserted(0, seriesList.size)

        if (seriesList.isNotEmpty())
            binding.tvLabelSeries.show()
    }

    private fun onItemClicked(serie: Serie) {
        startActivity(DetailActivity.newInstance(this, serie))
    }

    private fun loadPeopleSeries() {
        viewModel.loadPeopleSeries(people.id)
    }

    private fun showErrorView() {
        with(binding.errorView) {
            root.show()
            btTryAgain.setOnClickListener {
                loadPeopleSeries()
                root.hide()
            }
        }
    }

    companion object {
        private const val ARG_PEOPLE = "arg_people"

        fun newInstance(context: Context, people: People) = Intent(context, PeopleDetailActivity::class.java).apply {
            putExtra(ARG_PEOPLE, people)
        }
    }
}