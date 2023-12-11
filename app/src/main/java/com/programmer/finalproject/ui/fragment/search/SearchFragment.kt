package com.programmer.finalproject.ui.fragment.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.programmer.finalproject.R
import com.programmer.finalproject.adapter.CoursesAdapter
import com.programmer.finalproject.adapter.SearchResultAdapter
import com.programmer.finalproject.databinding.FragmentSearchBinding
import com.programmer.finalproject.ui.orders.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var mResultAdapter: SearchResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSearchBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareResultRV()
        observeSearchLiveData()

        var searchJob: Job? = null
        binding.etSearch.addTextChangedListener { searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                searchViewModel.searchMeals(searchQuery.toString())
                Log.d("lIST SEARCH",searchQuery.toString())
            }

        }
    }

    private fun observeSearchLiveData() {
        searchViewModel.observeSearchCoursesLiveData().observe(viewLifecycleOwner, Observer {
            mResultAdapter.mDiffer.submitList(it)
            if (it.isEmpty()) {
                binding.IllustrationEmpty.visibility = View.VISIBLE
            } else {
                binding.IllustrationEmpty.visibility = View.GONE
            }
        })
    }

    private fun prepareResultRV() {
        mResultAdapter = SearchResultAdapter()
        binding.rvSearch.apply {

            layoutManager=  GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = mResultAdapter
        }
    }

}