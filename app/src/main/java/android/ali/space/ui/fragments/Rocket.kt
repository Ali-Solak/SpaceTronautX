package android.ali.space.ui.fragments

import android.ali.space.R
import android.ali.space.ui.MainActivity
import android.ali.space.ui.ViewModel.SpaceViewModel
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_rocket.*

class Rocket : Fragment(R.layout.fragment_rocket) {


    lateinit var viewModel: SpaceViewModel
    val args: RocketArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        viewModel.refreshRockets()
        viewModel.getRocketFromDb(args.rocketNumber)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.rocket.observe(viewLifecycleOwner, Observer {
            rocket_title?.text = it.name
            Glide.with(this).load(it.images.firstOrNull()).into(picture)
            description?.text = "Height: ${it.height} Meters \n \n Cost Per Launch:  ${it.cost_per_launch} \n \n " +
                    "Stages:  ${it.stages} \n \n Weight in kg: ${it.kg} \n \n Description: \n \n ${it.description}"
        })

    }


}