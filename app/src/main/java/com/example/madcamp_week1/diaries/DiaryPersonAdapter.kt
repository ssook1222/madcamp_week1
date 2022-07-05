package com.example.madcamp_week1.diaries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week1.R
import kotlin.collections.ArrayList

class DiaryPersonAdapter(private val itemList: ArrayList<String>) : RecyclerView.Adapter<DiaryPersonAdapter.Holder>(){

    interface OnItemClickListener {
        fun onTextClick(view: View, name: String, pos: Int)
    }

    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.add_person_name_list, parent, false)
        return Holder(view)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        private val diaryContactName = itemView?.findViewById<TextView>(R.id.addDiaryName)

        fun bind(name: String) {
            // YYYY-MM-DD
            diaryContactName?.text=name
            diaryContactName?.setOnClickListener {
                listener?.onTextClick(diaryContactName, name, adapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
