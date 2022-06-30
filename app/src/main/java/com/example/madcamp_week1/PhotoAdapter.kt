package com.example.madcamp_week1

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class PhotoAdapter(val context:Context, val itemList: ArrayList<Photos>) :
    RecyclerView.Adapter<PhotoAdapter.Holder>(){
    interface OnItemClickListener{
        fun onImageClick(view: View, photos:Photos, pos: Int)
        fun onTextClick(view: View, photos:Photos, pos: Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_list, parent, false)
        return Holder(view)
    }
    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val photoImage = itemView?.findViewById<ImageButton>(R.id.photoContactImage)
        val photoName = itemView?.findViewById<TextView>(R.id.photoContactName)
        val photoText = itemView?.findViewById<LinearLayout>(R.id.choice_text)

        fun bind (photo: Photos, context: Context) {
            photoImage?.setImageResource(photo.resId)
            photoName?.text = photo.contactName
            photoImage!!.setOnClickListener{
                listener?.onImageClick(photoImage, photo, adapterPosition)
            }
            photoText!!.setOnClickListener{
                listener?.onTextClick(photoText, photo, adapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(itemList[position], context)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}