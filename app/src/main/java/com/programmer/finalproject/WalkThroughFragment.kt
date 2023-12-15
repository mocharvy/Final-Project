package com.programmer.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.programmer.finalproject.adapter.WalkthroughAdapter

class WalkThroughFragment : Fragment() {

    lateinit var wkAdapter: WalkthroughAdapter
    val dots = arrayOfNulls<TextView>(3)
    var currentpage: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_walk_through, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi button dengan findViewById
        val button = view.findViewById<ImageButton>(R.id.button)
        val masuk = view.findViewById<TextView>(R.id.txt_masuk)

        wkAdapter = WalkthroughAdapter(this)
        val viewPager = view.findViewById<ViewPager>(R.id.vp_walkthrough)
        viewPager.adapter = wkAdapter

        initAction()

        button.setOnClickListener{
            findNavController().navigate(R.id.action_walkThroughFragment_to_berandaFragment)
        }

        masuk.setOnClickListener {
            findNavController().navigate(R.id.action_walkThroughFragment_to_loginFragment)
        }


    }

    fun initAction(){
        val viewPager = requireView().findViewById<ViewPager>(R.id.vp_walkthrough)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageSelected(position: Int) {
                dotIndicator(position)
                currentpage = position
            }

        })
    }

    private fun dotIndicator(p: Int) {
    }
}
