package com.example.madcamp_week1

import android.media.Image
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class DiaryAdapter(val itemList: ArrayList<Diary>) :
    RecyclerView.Adapter<DiaryAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.diary_list, parent, false)
        return Holder(view)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        private val diaryDate = itemView?.findViewById<TextView>(R.id.diaryDate)
        private val diaryContactName = itemView?.findViewById<TextView>(R.id.diaryContactName)
        private val diaryImage = itemView?.findViewById<ImageView>(R.id.diaryImage)
        // private val diaryText = itemView?.findViewById<TextView>(R.id.diaryText)

        fun bind(diary: Diary) {
            // YYYY-MM-DD
            val ymd = diary.date.split("-").map { s -> s.toInt() }
            diaryDate?.text = String.format("%d년 %d월 %d일", ymd[0], ymd[1], ymd[2])
            diaryContactName?.text = diary.name
            diaryImage?.setImageResource(diary.resId)
            // diaryText?.text = diary.text
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
