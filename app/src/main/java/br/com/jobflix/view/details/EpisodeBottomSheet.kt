package br.com.jobflix.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.jobflix.data.model.Episode
import br.com.jobflix.databinding.BottomSheetEpDetailBinding
import br.com.jobflix.util.extensions.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EpisodeBottomSheet : BottomSheetDialogFragment() {

    private val binding by viewBinding(BottomSheetEpDetailBinding::inflate)

    private val episode by argument<Episode>(ARG_EPISODE)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            ivPoster.loadImageFromUrl(episode.image?.original)
            tvEpName.text = episode.name
            tvEpNumber.text = episode.formattedNumber()
            tvEpRuntime.text = episode.formattedRuntime()
            episode.summary?.let { tvEpSummary.setTextFromHtml(it) }
            fabClose.setOnClickListener { dismiss() }
        }
    }

    companion object {
        private const val ARG_EPISODE = "arg_episode"

        fun newInstance(episode: Episode) = EpisodeBottomSheet().withArgs {
            putParcelable(ARG_EPISODE, episode)
        }
    }
}