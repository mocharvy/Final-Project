package com.programmer.finalproject.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import coil.load
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.ActivityDetailPaymentBinding
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse3
import com.programmer.finalproject.model.payment.order.PutOrderRequest
import com.programmer.finalproject.ui.customlayout.PaymentSuccessDialog
import com.programmer.finalproject.ui.fragment.auth.LoginViewModel
import com.programmer.finalproject.ui.fragment.detailkelas.DetailKelasViewModel
import com.programmer.finalproject.ui.orders.HistoryPaymentFragment
import com.programmer.finalproject.ui.orders.HistoryPaymentFragment.Companion.ORDER_ID
import com.programmer.finalproject.ui.orders.OrdersViewModel
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class DetailPaymentActivity : AppCompatActivity() {
    private var expandedLayoutBank = false
    private var expandedLayoutCredit = false

    private lateinit var binding: ActivityDetailPaymentBinding

    private val detailKelasViewModel: DetailKelasViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
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

        detailKelasViewModel.courseId.value = intent.extras?.getString("courseId")
        requestDetailClassFromApi()

        binding.btPay.setOnClickListener {
            putOrder()
        }

    }

    private fun putOrder() {
        detailKelasViewModel.detailCourseResponse.observe(this) { response ->
            loginViewModel.token.observe(this) {
                if (it != null) {
                    val detailCourse = response.data!!
                    val orderID = detailCourse.data?.id


                    val putOrderRequest =
                        PutOrderRequest(HistoryPaymentFragment.COURSEID, "Credit Card")

                    if (orderID != null) {

                        showLoading(true)
                        Handler(Looper.getMainLooper()).postDelayed({
                            orderViewModel.putOrder("Bearer $it", ORDER_ID, putOrderRequest)
                        }, DELAY_TIME)
                        showLoading(false)

                        val dialogFragment = PaymentSuccessDialog()
                        dialogFragment.show(supportFragmentManager, "PaymentSuccessDialog")
                    }
                }
            }
        }
    }

    private fun requestDetailClassFromApi() {
        val courseId = detailKelasViewModel.courseId.value

        if (courseId != null) {
            detailKelasViewModel.getDetailCourse(courseId)
            observeDetailCourse()
        } else {
            Toast.makeText(this, "Course id is null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeDetailCourse() {
        detailKelasViewModel.detailCourseResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    showLoading(false)
                    val detailCourse = response.data!!
                    updateUI(detailCourse)

                }

                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Error -> {
                    showLoading(false)
                    Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show()
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val DELAY_TIME: Long = 2000
    }
}
