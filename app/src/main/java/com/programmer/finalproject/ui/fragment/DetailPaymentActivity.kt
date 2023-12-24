package com.programmer.finalproject.ui.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import coil.load
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.ActivityDetailKelasBinding
import com.programmer.finalproject.databinding.ActivityDetailPaymentBinding
import com.programmer.finalproject.databinding.FragmentDetailPaymentBinding
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse3
import com.programmer.finalproject.model.payment.OrderRequest
import com.programmer.finalproject.model.payment.order.PutOrderRequest
import com.programmer.finalproject.ui.DetailKelasActivity
import com.programmer.finalproject.ui.DetailKelasActivity.Companion.COURSE_ID
import com.programmer.finalproject.ui.bottomsheet.PurchasedBottomSheet
import com.programmer.finalproject.ui.fragment.auth.AuthViewModel
import com.programmer.finalproject.ui.fragment.detailkelas.DetailKelasViewModel
import com.programmer.finalproject.ui.orders.HistoryPaymentFragment
import com.programmer.finalproject.ui.orders.HistoryPaymentFragment.Companion.COURSEID
import com.programmer.finalproject.ui.orders.HistoryPaymentFragment.Companion.ORDER_ID
import com.programmer.finalproject.ui.orders.OrdersViewModel
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPaymentActivity : AppCompatActivity() {
    private var expandedLayoutBank = false
    private var expandedLayoutCredit = false
    private lateinit var binding: ActivityDetailPaymentBinding
    private val detailKelasViewModel: DetailKelasViewModel by viewModels()

    private val authViewModel: AuthViewModel by viewModels()
    private val orderViewModel: OrdersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
            putOrder()
        }
        requestDetailClassFromApi()

    }

    private fun putOrder() {
        detailKelasViewModel.detailCourseResponse.observe(this) { response ->
            authViewModel.token.observe(this) { it ->
                if (it != null) {
                    val detailCourse = response.data!!
                    val order_id = detailCourse.data?.id


                    val putOrderRequest = PutOrderRequest(HistoryPaymentFragment.COURSEID,"Credit Card")

                    if (order_id != null) {
                        orderViewModel.putOrder("Bearer " +it,ORDER_ID,putOrderRequest)
                        val navController = findNavController(R.id.nav_host)
                        navController.popBackStack()
                    }


                }
            }
        }
    }

    private fun requestDetailClassFromApi() {
        if(COURSEID == ""){
            detailKelasViewModel.getDetailCourse(COURSE_ID)

        }else{
            detailKelasViewModel.getDetailCourse(COURSEID)

        }

        observeDetailCourse()
    }

    private fun observeDetailCourse() {
        detailKelasViewModel.detailCourseResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val detailCourse = response.data!!
                    updateUI(detailCourse)
                }

                is NetworkResult.Loading -> {
                }

                is NetworkResult.Error -> {
                    Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show()
                    Log.e("DetailKelasFragment", "Error: ${response.message}")
                }

                else -> {
                    Log.e("DetailKelasFragment", "Error: ${response.message}")
                }
            }
        }

    }


    private fun updateUI(detailCourse: DetailCourseResponse3) {
        binding.apply {
            ivCourseImage.load(detailCourse.data?.category?.image)
            tvTitle.text = detailCourse.data?.name
            tvAuthor.text = detailCourse.data?.facilitator
            tvHarga.text=detailCourse.data?.price.toString()
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
