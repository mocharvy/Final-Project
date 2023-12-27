package com.programmer.finalproject.ui.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.programmer.finalproject.R
import com.programmer.finalproject.adapter.NotificationAdapter
import com.programmer.finalproject.adapter.TrackerClassAdapter
import com.programmer.finalproject.databinding.FragmentNotifikasiBinding
import com.programmer.finalproject.ui.bottomsheet.MustLoginBottomSheet
import com.programmer.finalproject.ui.fragment.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotifikasiFragment : Fragment() {
    private lateinit var binding : FragmentNotifikasiBinding

    private lateinit var notificationAdapter: NotificationAdapter
    private  val authViewModel: AuthViewModel by viewModels()
    private  val notificationViewModel: NotificationViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentNotifikasiBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNotification()
    }

    private fun getNotification() {
        authViewModel.token.observe(viewLifecycleOwner){
            if(it != null){

                notificationViewModel.getNotification("Bearer $it")

                notificationViewModel.getNotification.observe(viewLifecycleOwner) { list ->
                    notificationAdapter = NotificationAdapter{notif->
                        val notifId = notif.id
                        notificationViewModel.readNotification("Bearer " + it,notifId)
                        Toast.makeText(requireContext(), "Pesan telah dibaca", Toast.LENGTH_SHORT).show()
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
            }else{
                checkLogin()
            }
        }
    }
    private fun checkLogin() {
        authViewModel.token.observe(requireActivity()) { token ->
            if (token == null) {
               findNavController().navigate(R.id.action_notifikasiFragment_to_mustLoginBottomSheet)
            }
        }
    }

}