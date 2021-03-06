package android.ali.space.ui.fragments

import android.ali.space.R
import android.ali.space.adapters.ImageAdapter
import android.ali.space.ui.MainActivity
import android.ali.space.ui.ViewModel.SpaceViewModel
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.fragment_latest_launch.*


class LatestLaunch : Fragment(R.layout.fragment_latest_launch) {


    private lateinit var imageAdapter: ImageAdapter
    lateinit var viewModel: SpaceViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        viewModel.refreshlatestLaunch()
        viewModel.getLatestLaunchFromDb()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.latestLaunchLive.observe(viewLifecycleOwner, Observer {
            latestLaunch_title?.text = it?.name
            it?.pictures?.let { it1 -> loadCard(it1) }
            Glide.with(this).load(it?.patch).into(patch)
            description.text = it?.details
            launchDate.text = "Launch Date: ${it?.date_local}"
            it?.payload?.let { it1 -> viewModel.getPayloadFromDb(it1) }

        })

        viewModel.payload.observe(viewLifecycleOwner, Observer {
            payloadDetails.text =
                "Type: ${it?.type}, Customer: ${it?.customers?.joinToString(", ")}, Weight: ${it?.mass_kg} " +
                        "Manufacturers: ${it?.manufacturers?.joinToString(", ")}, Nationalities: ${it?.nationalities?.joinToString(", ")}"
        })

        viewModel.latestLaunchLive.observe(viewLifecycleOwner, Observer {

            lifecycle.addObserver(youtube_player_view)

            youtube_player_view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = it?.youtubeID
                    //
                    if (videoId != null) {
                        youTubePlayer.cueVideo(videoId, 0f)
                    }
                }
            })

        })

        viewModel.latestLaunchLive.observe(viewLifecycleOwner, Observer {

            val rocketNumber = it?.rocket

            rocketInfoButton.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("rocketNumber", rocketNumber)
                }
                findNavController().navigate(
                    R.id.action_latestLaunch_to_rocket,
                    bundle
                )
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
            }
        })

    }

    private fun loadCard(images: List<String>) {

        imageAdapter = ImageAdapter(images)

        viewPager.adapter = imageAdapter
        viewPager.setPadding(100, 0, 100, 0)

        TabLayoutMediator(tableLayout, viewPager) { tab, position ->

        }.attach()

    }


}
