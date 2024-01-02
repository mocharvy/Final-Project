package com.programmer.finalproject.ui.orders

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.programmer.finalproject.adapter.HistoryPaymentAdapter
import com.programmer.finalproject.databinding.FragmentHistoryPaymentBinding
import com.programmer.finalproject.ui.LoginActivity
import com.programmer.finalproject.ui.fragment.DetailPaymentActivity
import com.programmer.finalproject.ui.fragment.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HistoryPaymentFragment : Fragment() {
    private lateinit var binding: FragmentHistoryPaymentBinding

    private val authViewModel: AuthViewModel by viewModels()
    private val historyPaymentViewModel: OrdersViewModel by viewModels()
    private lateinit var historyPaymentAdapter: HistoryPaymentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryPaymentBinding.inflate(layoutInflater, container, false)
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
                        historyPaymentAdapter = HistoryPaymentAdapter { history ->
                            val courseID = history.course.id
                            COURSEID = courseID
                            ORDER_ID = history.id
                            Toast.makeText(requireContext(), courseID, Toast.LENGTH_SHORT).show()

                            val intent = Intent(requireContext(), DetailPaymentActivity::class.java)
                                .apply {
                                    putExtra("courseId", courseID)
                                }
                            startActivity(intent)
                        }

                        binding.rvHistoryPayment.adapter = historyPaymentAdapter
                        binding.rvHistoryPayment.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        historyPaymentAdapter.submitList(list?.data)
                        if (list?.data.isNullOrEmpty()) {
                            binding.clEmptyPayment.visibility = View.VISIBLE
                        } else {
                            binding.clEmptyPayment.visibility = View.GONE
                        }
                    }
                } else {
                    val intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }
    }

    companion object {
        var COURSEID = ""
        var ORDER_ID = ""
    }
}