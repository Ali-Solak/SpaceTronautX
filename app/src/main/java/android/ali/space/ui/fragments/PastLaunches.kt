package android.ali.space.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.ali.space.R
import android.ali.space.adapters.ImageAdapter
import android.ali.space.adapters.PLRecyclerViewAdapter
import android.ali.space.ui.MainActivity
import android.ali.space.ui.ViewModel.SpaceViewModel
import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_past_launches.*


class PastLaunches : Fragment(R.layout.fragment_past_launches) {

    private lateinit var pastLaunchesAdapter: PLRecyclerViewAdapter
    lateinit var viewModel: SpaceViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showProgressBar()
        viewModel = (activity as MainActivity).viewModel
        viewModel.refreshPastLaunches()
        viewModel.getPastLaunchesFromDb()
        hideProgressBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        pastLaunchesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("pastLaunchData", it)
            }
            findNavController().navigate(
                R.id.action_pastLaunches_to_pastLaunch,
                bundle
            )
        }

        viewModel.pastLaunches.observe(viewLifecycleOwner, Observer {

            if(it !=null) {
                pastLaunchesAdapter.differ.submitList(it.reversed())
            }
            else{
                Snackbar.make(view, "Internet Access Required", Snackbar.LENGTH_SHORT).show()
            }
        })



    }

    private fun setupRecyclerView() {
        pastLaunchesAdapter = PLRecyclerViewAdapter()
        rvPastLaunchesView.apply {
            adapter = pastLaunchesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


    private fun hideProgressBar() {
        progressBar?.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
    }


}