package android.ali.space.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.ali.space.R
import android.ali.space.adapters.UPLRecyclerViewAdapter
import android.ali.space.ui.MainActivity
import android.ali.space.ui.ViewModel.SpaceViewModel
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_upcoming_launches.*


class UpcomingLaunches : Fragment(R.layout.fragment_upcoming_launches) {

    lateinit var upcomingLaunchesAdapter: UPLRecyclerViewAdapter
    lateinit var viewModel: SpaceViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showProgressBar()
        viewModel = (activity as MainActivity).viewModel
        viewModel.refreshUpcomingLaunches()
        viewModel.getUpcomingLaunchesFromDb()
        hideProgressBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        upcomingLaunchesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("upcomingLaunchData", it)
            }
            findNavController().navigate(
                R.id.action_upcomingLaunches_to_upcomingLaunch2,
                bundle
            )
        }

        viewModel.upcomingLaunches.observe(viewLifecycleOwner, Observer {

            it.sortedByDescending { it.date_local.split("-").firstOrNull() }

            if(it !=null) {
                upcomingLaunchesAdapter.differ.submitList(it)
            }
            else{
                Snackbar.make(view, "Internet Access Required", Snackbar.LENGTH_SHORT).show()
            }
        })



    }

    private fun setupRecyclerView() {
        upcomingLaunchesAdapter = UPLRecyclerViewAdapter()
        rvUpcomingLaunches.apply {
            adapter = upcomingLaunchesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun hideProgressBar() {
        progressBar2?.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBar2?.visibility = View.VISIBLE
    }



}