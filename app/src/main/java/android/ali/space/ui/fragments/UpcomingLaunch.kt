package android.ali.space.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.ali.space.R
import android.ali.space.ui.MainActivity
import android.ali.space.ui.ViewModel.SpaceViewModel
import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_upcoming_launch.*


class UpcomingLaunch : Fragment(R.layout.fragment_upcoming_launch) {


    lateinit var viewModel: SpaceViewModel
    val args: UpcomingLaunchArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        viewModel.refreshUpcomingLaunches()
        viewModel.refreshPayloads()
        viewModel.getUpcomingLaunchesFromDb()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val upcomingLaunch = args.upcomingLaunchData

        upcomingLaunch_title.text = upcomingLaunch?.name
        Glide.with(this).load(upcomingLaunch?.patch).into(patch)
        description.text = upcomingLaunch?.details
        launchDateUpcoming.text = "Launch Date: ${upcomingLaunch?.date_local}"
        upcomingLaunch?.payloads?.let { viewModel.getPayloadFromDb(it) }


        val rocketNumber = upcomingLaunch?.rocket

        rocketInfoButton.setOnClickListener {
            val bundle = Bundle().apply {
                putString("rocketNumber", rocketNumber)
            }
            findNavController().navigate(
                R.id.action_upcomingLaunch_to_rocket,
                bundle
            )
        }


        viewModel.payload.observe(viewLifecycleOwner, Observer {
            payloadDetails.text =
                "Type: ${it?.type}, Customer: ${it?.customers}, Weight: ${it?.mass_kg} " +
                        "Manufacturers: ${it?.manufacturers}, Nationalities: ${it?.nationalities}"
        })

    }

}