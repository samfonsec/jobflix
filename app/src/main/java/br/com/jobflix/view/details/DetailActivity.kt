package br.com.jobflix.view.details

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.jobflix.R
import br.com.jobflix.data.model.Episode
import br.com.jobflix.data.model.Serie
import br.com.jobflix.databinding.ActDetailsBinding
import br.com.jobflix.util.*
import br.com.jobflix.util.Constants.FIRST_PAGE
import br.com.jobflix.util.extensions.extra
import br.com.jobflix.util.extensions.loadImageFromUrl
import br.com.jobflix.util.extensions.setTextFromHtml
import br.com.jobflix.util.extensions.viewBinding
import br.com.jobflix.viewModel.details.DetailsViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val binding by viewBinding(ActDetailsBinding::inflate)

    private val viewModel: DetailsViewModel by viewModel()

    private val serie by extra<Serie>(ARG_SERIE)

    private val episodesList: ArrayList<Episode> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        subscribeUi()
        buildUi()
        viewModel.loadEpisodes(serie.id, FIRST_PAGE)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun subscribeUi() {
        viewModel.onEpisodesResult().observe(this) { onEpisodesResult(it) }
        viewModel.onError().observe(this) { onError() }
        viewModel.onLoading().observe(this) { binding.pbLoadingEpisodes.isVisible = it }
    }

    private fun buildUi() {
        setupActionBar()
        setupEpisodesList()
        with(binding) {
            collapseToolbar.title = serie.name
            ivPoster.loadImageFromUrl(serie.image?.original)
            tvYear.text = serie.premiereYear()
            tvGenres.text = serie.formattedGenres()
            tvDescription.setTextFromHtml(serie.summary)
            tvSchedule.text = getString(
                R.string.schedule_date_time,
                serie.scheduleDays(),
                serie.schedule?.time
            )
            serie.rating?.average?.let {tvRating.text = getString(R.string.average_rating, it) }
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.tbDetails)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
    }

    private fun setupEpisodesList() {
        with(binding.rvEpisodes) {
            layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = EpisodeAdapter { onEpisodeClicked(it) }.apply {
                submitList(episodesList)
            }
        }
    }

    private fun onEpisodesResult(episodes: Map<Int, List<Episode>>) {
        val seasons = episodes.keys.map { getString(R.string.season_text, it) }
        with(binding.spSeasons) {
            adapter = ArrayAdapter(this@DetailActivity, android.R.layout.simple_spinner_item, seasons).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedSeason = position + 1
                    episodesList.clear()
                    episodes[selectedSeason]?.let { updateEpisodesList(it) }
                    binding.rvEpisodes.scrollToPosition(0)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    private fun onError() {
        Snackbar.make(binding.root, "Error!", Snackbar.LENGTH_SHORT).show() // TODO
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateEpisodesList(episodes: List<Episode>) {
        episodesList += episodes
        binding.rvEpisodes.adapter?.notifyDataSetChanged()
    }

    private fun onEpisodeClicked(episode: Episode) {
        EpisodeBottomSheet.newInstance(episode).show(supportFragmentManager, TAG)
    }

    companion object {
        private const val ARG_SERIE = "arg_serie"
        private const val TAG = "EpisodeBottomSheet"

        fun newInstance(context: Context, serie: Serie) = Intent(context, DetailActivity::class.java).apply {
            putExtra(ARG_SERIE, serie)
        }
    }
}