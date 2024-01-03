package com.programmer.finalproject.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.programmer.finalproject.R
import com.programmer.finalproject.adapter.NotificationAdapter
import com.programmer.finalproject.databinding.FragmentNotifikasiBinding
import com.programmer.finalproject.ui.fragment.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotifikasiFragment : Fragment() {
    private lateinit var binding: FragmentNotifikasiBinding

    private lateinit var notificationAdapter: NotificationAdapter
    private val authViewModel: AuthViewModel by viewModels()
    private val notificationViewModel: NotificationViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotifikasiBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNotification()

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvNotifcation)
    }

    private fun getNotification() {
        authViewModel.token.observe(viewLifecycleOwner) {
            if (it != null) {

                notificationViewModel.getNotification("Bearer $it")

                notificationViewModel.getNotification.observe(viewLifecycleOwner) { list ->
                    notificationAdapter = NotificationAdapter { notif ->
                        val notifId = notif.id
                        notificationViewModel.readNotification("Bearer $it", notifId)
                        Toast.makeText(requireContext(), "Pesan telah dibaca", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().navigate(R.id.action_notifikasiFragment_self)

                    }

                    binding.rvNotifcation.adapter = notificationAdapter
                    binding.rvNotifcation.layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    notificationAdapter.submitList(list?.data)
                    if (list?.data.isNullOrEmpty()) {
                        binding.clEmptyNotif.visibility = View.VISIBLE
                    } else {
                        binding.clEmptyNotif.visibility = View.GONE
                    }

                }
            }
        }
    }

    private val itemTouchHelperCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            @Suppress("DEPRECATION")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                notificationAdapter.deleteItem(position)
                Toast.makeText(requireContext(), "Notifikasi dihapus", Toast.LENGTH_SHORT).show()
            }
        }

}