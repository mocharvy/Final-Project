package com.programmer.finalproject.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.DialogConfirmationOrderBinding
import com.programmer.finalproject.databinding.DialogLogoutBinding
import com.programmer.finalproject.databinding.FragmentDetailPaymentBinding
import com.programmer.finalproject.model.payment.OrderRequest
import com.programmer.finalproject.model.user.password.ChangePasswordRequest
import com.programmer.finalproject.ui.bottomsheet.PurchasedBottomSheet
import com.programmer.finalproject.ui.fragment.auth.AuthViewModel
import com.programmer.finalproject.ui.orders.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPaymentFragment : Fragment() {

    private lateinit var binding: FragmentDetailPaymentBinding
    private var expandedLayoutBank = false
    private var expandedLayoutCredit = false
    private val authViewModel: AuthViewModel by viewModels()
    private val orderViewModel: OrdersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailPaymentBinding.inflate(inflater, container, false)

        setupExpandableLayout(
            binding.cvBank,
            binding.ivExpand,
            binding.expandLayout,
            R.drawable.ic_expand_less,
            R.drawable.ic_expand,
            expandedLayoutBank
        ) {
            expandedLayoutBank = it
            if (it) {
                collapseExpandableLayout(
                    binding.cvCreditCard,
                    binding.ivExpandCredit,
                    binding.expandLayoutCredit,
                    R.drawable.ic_expand
                )
            }
        }

        setupExpandableLayout(
            binding.cvCreditCard,
            binding.ivExpandCredit,
            binding.expandLayoutCredit,
            R.drawable.ic_expand_less,
            R.drawable.ic_expand,
            expandedLayoutCredit
        ) {
            expandedLayoutCredit = it
            if (it) {
                collapseExpandableLayout(
                    binding.cvBank,
                    binding.ivExpand,
                    binding.expandLayout,
                    R.drawable.ic_expand
                )
            }
        }

        binding.btPay.setOnClickListener {
            orderCourses()
        }
        return binding.root
    }

    private fun orderCourses() {
        authViewModel.token.observe(viewLifecycleOwner){
            if (it != null) {
                    val orderRequest = OrderRequest(binding.courseId.text.toString())
                    orderViewModel.orderCourses("Bearer $it",orderRequest)
                orderViewModel.isError.observe(viewLifecycleOwner){
                    if(it){
                        Toast.makeText(requireContext(), "gagal melakukan order kursus", Toast.LENGTH_SHORT).show()
                    }else{
                        showConfirmation()
                    }

                }
            }
        }
    }

    private fun showConfirmation() {
        val confirmation = DialogConfirmationOrderBinding.inflate(LayoutInflater.from(requireContext()))
        val confirmationDialogBuilder = AlertDialog.Builder(requireContext(), R.style.RoundedCornerDialog)
            .setView(confirmation.root)
        confirmationDialogBuilder.setCancelable(true)
        val logoutDialog = confirmationDialogBuilder.show()
        confirmation.btnBeliKelas.setOnClickListener {
//            authViewModel.logout()

            logoutDialog.dismiss()
            val purchasedBottomSheet = PurchasedBottomSheet()
            purchasedBottomSheet.show(
                requireActivity().supportFragmentManager,
                purchasedBottomSheet.tag
            )

        }
        confirmation.btnBatal.setOnClickListener {
            logoutDialog.dismiss()
        }
    }

    private fun setupExpandableLayout(
        cardView: CardView,
        arrow: ImageView,
        expandLayout: View,
        expandLessIcon: Int,
        expandIcon: Int,
        expandedLayout: Boolean,
        onStateChanged: (Boolean) -> Unit
    ) {
        arrow.setOnClickListener {
            val transition = AutoTransition()
            TransitionManager.beginDelayedTransition(cardView, transition)
            if (expandLayout.visibility == View.VISIBLE) {
                expandLayout.visibility = View.GONE
                arrow.setImageResource(expandIcon)
                onStateChanged(false)
            } else {
                expandLayout.visibility = View.VISIBLE
                arrow.setImageResource(expandLessIcon)
                onStateChanged(true)
            }
        }

        expandLayout.visibility = if (expandedLayout) View.VISIBLE else View.GONE
        arrow.setImageResource(if (expandedLayout) expandLessIcon else expandIcon)
    }

    private fun collapseExpandableLayout(
        cardView: CardView,
        arrow: ImageView,
        expandLayout: View,
        expandIcon: Int
    ) {
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(cardView, transition)
        expandLayout.visibility = View.GONE
        arrow.setImageResource(expandIcon)
    }
}