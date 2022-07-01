package com.example.madcamp_week1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ChoicePhotoAdapter(val context: Context, val itemList: ArrayList<ChoicePhotos>) :
    RecyclerView.Adapter<ChoicePhotoAdapter.Holder>(){
    interface OnItemClickListener{
        fun onItemClick(view: View, choicePhotos:ChoicePhotos, pos: Int) //데이터 세이브
    }
    private var listener : ChoicePhotoAdapter.OnItemClickListener? = null
    fun setOnItemClickListener(listener: ChoicePhotoAdapter.OnItemClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.choice_photo_list, parent, false)
        return Holder(view)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val choicePhotoImage = itemView?.findViewById<ImageView>(R.id.choice_image)

        fun bind (choicePhotos: ChoicePhotos) {
            choicePhotoImage?.setImageResource(choicePhotos.resId)
            choicePhotoImage!!.setOnClickListener{
                listener?.onItemClick(choicePhotoImage,choicePhotos,adapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}