package com.example.madcamp_week1.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week1.diaries.DiaryAdapter
import com.example.madcamp_week1.R

class ContactAdapter(val itemList: ArrayList<Contacts>) : RecyclerView.Adapter<ContactAdapter.Holder>(){
    interface OnItemClickListener {
        fun onCardViewClick(view: View, contacts: Contacts, pos: Int)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contacts_list, parent, false)
        return Holder(view)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        private val contactName = itemView?.findViewById<TextView>(R.id.contactName)
        private val phoneNumber = itemView?.findViewById<TextView>(R.id.phoneNumber)
        private val startDate = itemView?.findViewById<TextView>(R.id.startDate)
        private val cardView = itemView?.findViewById<CardView>(R.id.card_view)

        fun bind (contacts: Contacts) {
            contactName?.text = contacts.contactName
            phoneNumber?.text = contacts.phoneNumber
            startDate?.text = contacts.startDate

            cardView?.setOnClickListener {
                listener?.onCardViewClick(itemView, contacts, adapterPosition)
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