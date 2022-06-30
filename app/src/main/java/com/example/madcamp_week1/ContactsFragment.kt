package com.example.madcamp_week1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactsFragment : Fragment() {
    lateinit var recyclerView : RecyclerView

    var contactsList = arrayListOf<Contacts>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_contacts, container, false)
//        contactsList.add(Contacts("ssook","010-1111-1111","06/29"))
//        contactsList.add(Contacts("jylee","010-1201-1201","06/29"))
        val contactAdapter = ContactAdapter(contactsList)

        recyclerView = rootView.findViewById(R.id.recyclerView!!)as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = contactAdapter

        contactAdapter.notifyDataSetChanged()
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}