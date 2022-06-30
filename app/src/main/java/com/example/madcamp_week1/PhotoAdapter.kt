package com.example.madcamp_week1

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PhotoAdapter(val itemList: ArrayList<Photos>) :
    RecyclerView.Adapter<PhotoAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_list, parent, false)
        return Holder(view)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val photoImage = itemView?.findViewById<ImageView>(R.id.photoContactImage)
        val photoName = itemView?.findViewById<TextView>(R.id.photoContactName)


        fun bind (photo: Photos) {
            photoImage?.setImageResource(photo.resId)
            photoName?.text = photo.contactName
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}