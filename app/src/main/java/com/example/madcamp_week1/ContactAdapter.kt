package com.example.madcamp_week1

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(val itemList: ArrayList<Contacts>) :
    RecyclerView.Adapter<ContactAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contacts_list, parent, false)
        return Holder(view)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val contactName = itemView?.findViewById<TextView>(R.id.contactName)
        val phoneNumber = itemView?.findViewById<TextView>(R.id.phoneNumber)
        val startDate = itemView?.findViewById<TextView>(R.id.startDate)

        fun bind (contacts: Contacts) {
            contactName?.text = contacts.contactName
            phoneNumber?.text = contacts.phoneNumber
            startDate?.text = contacts.startDate
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Log.d("ha","리사이클러뷰 실행")
        holder?.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}