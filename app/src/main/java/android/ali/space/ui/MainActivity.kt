package android.ali.space.ui

import android.ali.space.R
import android.ali.space.ui.ViewModel.SpaceViewModel
import android.ali.space.ui.ViewModel.SpaceViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration : AppBarConfiguration
    lateinit var viewModel: SpaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModelProviderFactory = SpaceViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(SpaceViewModel::class.java)

        bottomNavigationView.setupWithNavController(navHostfragment.findNavController())
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = navHostfragment.findNavController()
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

}