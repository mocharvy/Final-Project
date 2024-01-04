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
import com.programmer.finalproject.ui.bottomsheet.PurchasedBottomSheet
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

    private var methode = "Credit Card"
    private var typeClass = ""

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

        binding.btCredit.setOnClickListener {
            methode = "Credit Card"
            Toast.makeText(this, "Pembayaran menggunakan kartu kredit dipilih", Toast.LENGTH_SHORT)
                .show()
        }

        binding.btTransfer.setOnClickListener {
            methode = "Bank Transfer"
            Toast.makeText(this, "Pembayaran menggunakan bank transfer dipilih", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private fun putOrder() {
        detailKelasViewModel.detailCourseResponse.observe(this) { response ->
            loginViewModel.token.observe(this) {
                if (it != null) {
                    val detailCourse = response.data!!
                    val orderID = detailCourse.data?.id

                    if (typeClass == "Free") {
                        methode = "Bank Transfer"
                    }

                    if (methode == "Credit Card") {
                        if (validateMethode()) {
                            val putOrderRequest =
                                PutOrderRequest(HistoryPaymentFragment.COURSEID, methode)

                            if (orderID != null) {
                                Handler(Looper.getMainLooper()).postDelayed({
                                    showLoading(true)
                                }, DELAY_TIME)
                                orderViewModel.putOrder("Bearer $it", ORDER_ID, putOrderRequest)
                                showLoading(false)

                                val purchasedBottomSheet = PurchasedBottomSheet()
                                purchasedBottomSheet.show(
                                    this.supportFragmentManager,
                                    purchasedBottomSheet.tag
                                )
                            }
                        } else {
                            Toast.makeText(
                                this,
                                "Mohon lengkapi kembali data yang diperlukan",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        val putOrderRequest =
                            PutOrderRequest(HistoryPaymentFragment.COURSEID, methode)

                        if (orderID != null) {
                            showLoading(true)
                            Handler(Looper.getMainLooper()).postDelayed({
                                orderViewModel.putOrder("Bearer $it", ORDER_ID, putOrderRequest)
                            }, DELAY_TIME)
                            showLoading(false)

                            val purchasedBottomSheet = PurchasedBottomSheet()
                            purchasedBottomSheet.show(
                                this.supportFragmentManager,
                                purchasedBottomSheet.tag
                            )
                        }
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
            Toast.makeText(this, "Course id kosong", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Log.e("DetailPaymentFragment", "Error: ${response.message}")
                }
            }
        }

    }


    private fun updateUI(detailCourse: DetailCourseResponse3) {
        val fullPrice = detailCourse.data?.price
        val ppn = fullPrice?.times(0.10)
        val ppnPrice = ppn?.let {
            fullPrice.plus(it)
        }

        val decimalFormat = DecimalFormat("#.##")
        val formattedPpn = decimalFormat.format(ppn)
        val formattedPpnPrice = decimalFormat.format(ppnPrice)

        val type = detailCourse.data?.type
        typeClass = type.toString()

        binding.tvDesc.text = detailCourse.data?.name
        binding.tvTitle.text = detailCourse.data?.name
        binding.tvAuthor.text = detailCourse.data?.facilitator
        binding.ivCourseImage.load(detailCourse.data?.category?.image)
        binding.tvHarga.text = detailCourse.data?.price.toString()
        binding.tvPpn.text = formattedPpn.toString()
        binding.tvTotal.text = formattedPpnPrice.toString()

        if (type == "Free") {
            binding.btPay.text = getString(R.string.ikuti_kelas)
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun validateMethode(): Boolean {
        val cardNumber = binding.etCardNumber.text.toString()
        val cardHolder = binding.etCardHolder.text.toString()
        val cardCVV = binding.etCardCvv.text.toString()
        val cardExpire = binding.etExpire.text.toString()

        return if (cardNumber.isEmpty() || cardHolder.isEmpty() || cardCVV.isEmpty() || cardExpire.isEmpty()) {
            Toast.makeText(this, "Semua data harus diisi", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    companion object {
        const val DELAY_TIME: Long = 1000
    }
}
