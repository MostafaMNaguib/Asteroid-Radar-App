package com.mostafa.udacity.asteroidradarapp.ui.main

import android.os.Bundle
import android.view.*
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mostafa.udacity.asteroidradarapp.R
import com.mostafa.udacity.asteroidradarapp.databinding.FragmentMainBinding
import com.mostafa.udacity.asteroidradarapp.ui.main.adapter.AsteroidItemClickListener
import com.mostafa.udacity.asteroidradarapp.ui.main.adapter.AsteroidsListAdapter
import com.mostafa.udacity.asteroidradarapp.utils.bindImagePictureOfDay

class MainFragment: Fragment()
{

    private lateinit var binding: FragmentMainBinding

    private lateinit var asteroidsAdapter: AsteroidsListAdapter

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupUi()

        setHasOptionsMenu(true)

    }

    private fun setupUi(){

        // recycle view
        asteroidsAdapter = AsteroidsListAdapter(AsteroidItemClickListener { asteroidId ->
            viewModel.onAsteroidItemClick(asteroidId)
        })

        binding.asteroidRecycler.adapter = asteroidsAdapter
        viewModel.asteroids.observe(viewLifecycleOwner) { asteroids ->
            asteroidsAdapter.submitList(asteroids)
        }

        // navigation
        viewModel.navigateToDetailFragment.observe(viewLifecycleOwner) { asteroid ->
            asteroid?.let {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.onDetailFragmentNavigated()
            }
        }
        
        viewModel.pictureOfDay.observe(viewLifecycleOwner){
            bindImagePictureOfDay(
                binding.activityMainImageOfTheDay,it
            )
        }




    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.show_all_menu -> {
                viewModel.asteroidsFromToday.observe(viewLifecycleOwner){
                    asteroidsAdapter.submitList(it)
                }
            }
            R.id.show_rent_menu -> {
                viewModel.todayAsteroids.observe(viewLifecycleOwner){
                    asteroidsAdapter.submitList(it)
                }
            }
            R.id.show_buy_menu -> {
                viewModel.asteroids.observe(viewLifecycleOwner){
                    asteroidsAdapter.submitList(it)
                }
            }
        }
        return true
    }

}