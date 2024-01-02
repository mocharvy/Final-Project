package com.programmer.finalproject.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import coil.load
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.DialogConfirmationOrderBinding
import com.programmer.finalproject.databinding.FragmentDetailPaymentBinding
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse3
import com.programmer.finalproject.model.payment.OrderRequest
import com.programmer.finalproject.ui.bottomsheet.PurchasedBottomSheet
import com.programmer.finalproject.ui.fragment.auth.AuthViewModel
import com.programmer.finalproject.ui.fragment.detailkelas.DetailKelasViewModel
import com.programmer.finalproject.ui.orders.OrdersViewModel
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class DetailPaymentFragment : Fragment() {

    private lateinit var binding: FragmentDetailPaymentBinding
    private var expandedLayoutBank = false
    private var expandedLayoutCredit = false

    private val authViewModel: AuthViewModel by viewModels()
    private val orderViewModel: OrdersViewModel by viewModels()
    private val detailKelasViewModel: DetailKelasViewModel by viewModels()

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

        detailKelasViewModel.courseId.value = arguments?.getString("courseId")
        requestDetailClassFromApi()

        binding.btPay.setOnClickListener {
            orderCourses()
        }
        return binding.root
    }

    private fun requestDetailClassFromApi() {
        val courseId = detailKelasViewModel.courseId.value

        if (courseId != null) {
            detailKelasViewModel.getDetailCourse(courseId)
            observeDetailCourse()
        } else {
            Toast.makeText(requireContext(), "Course id is null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeDetailCourse() {
        detailKelasViewModel.detailCourseResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    //hideLoading()
                    val detailCourse = response.data!!
                    updateUI(detailCourse)

                }

                is NetworkResult.Loading -> {
                    //showLoading()
                }

                is NetworkResult.Error -> {
                    //hideLoading()
                    Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show()
                    Log.e("DetailPaymentFragment", "Error: ${response.message}")
                }

                else -> {
                    Log.e("DetailPaymentFragment", "Error: ${response.message}")
                }
            }
        }
    }

    private fun updateUI(detailCourse: DetailCourseResponse3) {

        val fullPrice = detailCourse.data?.price
        Log.d("FULL PRICE", "$fullPrice")
        val ppn = fullPrice?.times(0.10)
        Log.d("PPN", "$ppn")
        val ppnPrice = ppn?.let {
            fullPrice.plus(it)
        }

        val decimalFormat = DecimalFormat("#.##")
        val formattedPpn = decimalFormat.format(ppn)
        val formattedPpnPrice = decimalFormat.format(ppnPrice)

        binding.tvDesc.text = detailCourse.data?.name
        binding.tvTitle.text = detailCourse.data?.name
        binding.tvAuthor.text = detailCourse.data?.facilitator
        binding.ivCourseImage.load(detailCourse.data?.category?.image)
        binding.tvHarga.text = detailCourse.data?.price.toString()
        binding.tvPpn.text = formattedPpn.toString()
        binding.tvTotal.text = formattedPpnPrice.toString()
        binding.courseId.text = detailKelasViewModel.courseId.value


    }

    private fun orderCourses() {
        authViewModel.token.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.courseId.text = detailKelasViewModel.courseId.value
                
                val orderRequest = OrderRequest(binding.courseId.text.toString())
                orderViewModel.orderCourses("Bearer $it", orderRequest)
                orderViewModel.isError.observe(viewLifecycleOwner) { isError ->
                    if (isError) {
                        Toast.makeText(
                            requireContext(),
                            "gagal melakukan order kursus",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        showConfirmation()
                    }

                }
            }
        }
    }

    private fun showConfirmation() {
        val confirmation =
            DialogConfirmationOrderBinding.inflate(LayoutInflater.from(requireContext()))
        val confirmationDialogBuilder =
            AlertDialog.Builder(requireContext(), R.style.RoundedCornerDialog)
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