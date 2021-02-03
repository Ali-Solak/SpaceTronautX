package android.ali.space.ui.fragments

import android.ali.space.R
import android.ali.space.adapters.ImageAdapter
import android.ali.space.ui.MainActivity
import android.ali.space.ui.ViewModel.SpaceViewModel
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.fragment_latest_launch.*
import kotlinx.android.synthetic.main.fragment_latest_launch.description
import kotlinx.android.synthetic.main.fragment_latest_launch.latestLaunch_title
import kotlinx.android.synthetic.main.fragment_latest_launch.launchDate
import kotlinx.android.synthetic.main.fragment_latest_launch.patch
import kotlinx.android.synthetic.main.fragment_latest_launch.payloadDetails
import kotlinx.android.synthetic.main.fragment_latest_launch.rocketInfoButton
import kotlinx.android.synthetic.main.fragment_latest_launch.tableLayout
import kotlinx.android.synthetic.main.fragment_latest_launch.viewPager
import kotlinx.android.synthetic.main.fragment_latest_launch.youtube_player_view
import kotlinx.android.synthetic.main.fragment_past_launch.*


class PastLaunch : Fragment(R.layout.fragment_past_launch) {

    private lateinit var imageAdapter: ImageAdapter
    lateinit var viewModel: SpaceViewModel
    val args: PastLaunchArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        viewModel.refreshPastLaunches()
        viewModel.refreshPayloads()
        viewModel.getPastLaunchesFromDb()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pastLaunch = args.pastLaunchData

        pastLaunch_title.text = pastLaunch?.name
        pastLaunch?.pictures?.let { loadCard(it) }
        Glide.with(this).load(pastLaunch?.patch).into(patch)
        description.text = pastLaunch?.details
        launchDate.text = "Launch Date: ${pastLaunch?.date_local}"
        pastLaunch?.payload?.let { viewModel.getPayloadFromDb(it) }


        lifecycle.addObserver(youtube_player_view)

        youtube_player_view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = pastLaunch?.youtubeId
                if (videoId != null) {
                    //
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            }
        })

        val rocketNumber = pastLaunch?.rocket

        rocketInfoButton.setOnClickListener {
            val bundle = Bundle().apply {
                putString("rocketNumber", rocketNumber)
            }
            findNavController().navigate(
                R.id.action_pastLaunch_to_rocket,
                bundle
            )
        }


        viewModel.payload.observe(viewLifecycleOwner, Observer {
            payloadDetails.text =
                "Type: ${it.type}, Customer: ${it.customers}, Weight: ${it.mass_kg} " +
                        "Manufacturers: ${it.manufacturers}, Nationalities: ${it.nationalities}"
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
