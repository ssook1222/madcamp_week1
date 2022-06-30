package com.example.madcamp_week1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(val context: Context, val itemList: ArrayList<Contacts>) :
    RecyclerView.Adapter<ContactAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.contacts_list, parent, false)
        return Holder(view)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val contactName = itemView?.findViewById<TextView>(R.id.contactName)
        val phoneNumber = itemView?.findViewById<TextView>(R.id.phoneNumber)
        val startDate = itemView?.findViewById<TextView>(R.id.startDate)

        fun bind (contacts: Contacts, context: Context) {
            contactName?.text = contacts.contactName
            phoneNumber?.text = contacts.phoneNumber
            startDate?.text = contacts.startDate
        }

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(itemList[position], context)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}