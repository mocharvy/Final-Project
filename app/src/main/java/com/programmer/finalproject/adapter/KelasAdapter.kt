package com.programmer.finalproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.programmer.finalproject.R

class KelasAdapter(private val fragmentList: List<Fragment>) :
    RecyclerView.Adapter<KelasAdapter.FragmentViewHolder>() {

    inner class FragmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)
        // Tambahkan referensi ke elemen UI lainnya di sini
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FragmentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_kelas, parent, false)
        return FragmentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FragmentViewHolder, position: Int) {
        val currentItem = fragmentList[position]

//        holder.titleTextView.text = currentItem.title
//        holder.statusTextView.text = currentItem.status
        // Atur nilai elemen UI lainnya dengan data dari currentItem
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }
}
