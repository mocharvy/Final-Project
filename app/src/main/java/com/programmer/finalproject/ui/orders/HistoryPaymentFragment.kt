package com.programmer.finalproject.ui.orders

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.programmer.finalproject.R
import com.programmer.finalproject.adapter.CategoryAdapter
import com.programmer.finalproject.adapter.CoursesAdapter
import com.programmer.finalproject.adapter.HistoryPaymentAdapter
import com.programmer.finalproject.databinding.FragmentHistoryPaymentBinding
import com.programmer.finalproject.ui.fragment.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HistoryPaymentFragment : Fragment() {
    private lateinit var binding : FragmentHistoryPaymentBinding

    private val authViewModel: AuthViewModel by viewModels()
    private val historyPaymentViewModel: OrdersViewModel by viewModels()
    private lateinit var historyPaymentAdapter: HistoryPaymentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryPaymentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getHistoryPayment()
    }

    private fun getHistoryPayment() {
        authViewModel.token.observe(viewLifecycleOwner) {
            authViewModel.token.observe(viewLifecycleOwner) {
                if (it != null) {
                    Log.d("TOKEN", it)

                    historyPaymentViewModel.getHistoryPayment("Bearer $it")
                    historyPaymentViewModel.getListHistoryPayment.observe(viewLifecycleOwner) { list ->
                        historyPaymentAdapter = HistoryPaymentAdapter()

                        binding.rvHistoryPayment.adapter = historyPaymentAdapter
                        binding.rvHistoryPayment.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        historyPaymentAdapter.submitList(list?.data)

                    }
                } else {

//                    binding.progressBar.visibility = View.GONE

                }
            }
        }
    }

}