package com.programmer.finalproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import com.programmer.finalproject.R
import com.programmer.finalproject.WalkThroughFragment

class WalkthroughAdapter(private val context: WalkThroughFragment): PagerAdapter() {

    private val imgArray: IntArray = intArrayOf(R.drawable.analytics, R.drawable.project_planning, R.drawable.share_location)
    private val titleArray: Array<String> = arrayOf("Analytics", "Project Planning", "Share Location")

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return imgArray.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View = LayoutInflater.from(context.requireContext()).inflate(R.layout.slide_walkthrough, container,false)

        val txtTitle: TextView = view.findViewById(R.id.tv_title)
        val img: ImageView = view.findViewById(R.id.iv_img)

        txtTitle.text = titleArray[position]
        img.setImageDrawable(ContextCompat.getDrawable(context.requireContext(), imgArray[position]))
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view: View = `object` as View
        container.removeView(view)
    }
}
