package com.example.madcamp_week1.diaries

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week1.R
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class DiaryAdapter(val context: Context, private val itemList: ArrayList<Diary>) : RecyclerView.Adapter<DiaryAdapter.Holder>(), JsonSerializer<Diary> {

    interface OnItemClickListener {
        fun onTextClick(view: View, diary: Diary, pos: Int)
    }

    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.diary_list, parent, false)
        return Holder(view)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        private val diaryDate = itemView?.findViewById<TextView>(R.id.diaryDate)
        private val diaryContactName = itemView?.findViewById<TextView>(R.id.diaryContactName)
        private val diaryImage = itemView?.findViewById<ImageView>(R.id.diaryImage)
        private val diaryTitle = itemView?.findViewById<TextView>(R.id.diaryTitle)

        fun bind(diary: Diary, context: Context) {
            // YYYY-MM-DD
            val ymd = diary.date.split("-").map { s -> s.toInt() }
            diaryDate?.text = String.format("%d년 %d월 %d일", ymd[0], ymd[1], ymd[2])
            diaryContactName?.text = diary.name
            diaryImage?.setImageURI(diary.uri)
            diaryTitle?.text = diary.title

            diaryTitle?.setOnClickListener {
                listener?.onTextClick(diaryTitle, diary, adapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemList[position], context)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun serialize(src: Diary?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        val obj = JsonObject()
        obj.addProperty("date", src?.date)
        obj.addProperty("name", src?.name)
        obj.addProperty("uri", src?.uri.toString())
        obj.addProperty("title", src?.title)
        return obj
    }
}
