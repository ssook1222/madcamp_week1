package com.example.madcamp_week1.photos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week1.R

class ChoicePhotoAdapter(val context: Context, private val itemList: ArrayList<ChoicePhotos>) :
    RecyclerView.Adapter<ChoicePhotoAdapter.Holder>(){
    interface OnItemClickListener{
        fun onItemClick(view: View, choicePhotos: ChoicePhotos, pos: Int) //데이터 세이브
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.choice_photo_list, parent, false)
        return Holder(view)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        private val choicePhotoImage = itemView?.findViewById<ImageView>(R.id.choice_image)

        fun bind (choicePhotos: ChoicePhotos) {
            choicePhotoImage?.setImageURI(choicePhotos.uri)
            choicePhotoImage!!.setOnClickListener{
                listener?.onItemClick(choicePhotoImage,choicePhotos,adapterPosition)
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