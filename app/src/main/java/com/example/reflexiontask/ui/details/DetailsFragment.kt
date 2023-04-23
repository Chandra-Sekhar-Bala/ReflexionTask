package com.example.reflexiontask.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.example.reflexiontask.Constants
import com.example.reflexiontask.R
import com.example.reflexiontask.databinding.FragmentDetailsBinding
import com.example.reflexiontask.model.Movie
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var binding: FragmentDetailsBinding
    private var youtubeLink: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: DetailsFragmentArgs by navArgs()
        setupUI(args.movie)
        viewModel.currentMovie = args.movie
        viewModel.checkIfAlreadyExist()
    }

    override fun onResume() {
        super.onResume()
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnWatchTrailer.setOnClickListener {
            sendUserToYoutube()
        }
        viewModel.existInDB.observe(this) {
            val drawable = when (it) {
                true -> ContextCompat.getDrawable(requireContext(), R.drawable.saved_filled)
                false -> ContextCompat.getDrawable(requireContext(), R.drawable.saved)
            }
            binding.saveAsFavorite.setImageDrawable(drawable)
        }

        binding.saveAsFavorite.setOnClickListener{
            viewModel.saveToDB()
        }
    }

    private fun setupUI(data: Movie) {
        youtubeLink = Constants.YOUTUBE_PREFIX + data.YouTubeTrailer
        binding.title.text = data.Title
        binding.rating.text = String.format("%s/10", data.Rating)
        binding.release.text = data.Year
        binding.runtime.text = data.Runtime
        val cast = data.Cast.split("|")
        cast.let {
            val casts = listOf(
                binding.cast1,
                binding.cast2,
                binding.cast3,
                binding.cast4,
                binding.cast5,
            )
            for (i in it.indices) {
                if (i > 6) break
                casts[i].text = it[i]
                casts[i].visibility = View.VISIBLE
            }
        }
        Glide.with(this)
            .load(data.MoviePoster)
            .placeholder(R.drawable.ic_movie)
            .signature(ObjectKey(data.IMDBID))
            .into(binding.moviePoster)
            .clearOnDetach()
        binding.about.text = data.Summary
    }

    private fun sendUserToYoutube() {
        if (youtubeLink != null) {
            // Open in YouTube app if available
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            intent.setPackage("com.google.android.youtube")
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            } else {
                // Open in web browser if YouTube app is not installed
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink)))
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Trailer is not available at this moment",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}