package com.example.madcamp_week1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONTokener

class ContactsFragment : Fragment() {
    lateinit var recyclerView : RecyclerView

    var contactsList = arrayListOf<Contacts>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_contacts, container, false)

        if (contactsList.isEmpty()) {
            val contactsJsonString: String = requireActivity().assets.open("contacts.json").bufferedReader().use {
                it.readText()
            }
            val contactsJsonArray = JSONTokener(contactsJsonString).nextValue() as JSONArray
            for (i in 0 until contactsJsonArray.length()) {
                val name = contactsJsonArray.getJSONObject(i).getString("name")
                val phoneNumber = contactsJsonArray.getJSONObject(i).getString("phoneNumber")
                val startDate = contactsJsonArray.getJSONObject(i).getString("startDate")
                contactsList.add(Contacts(name, phoneNumber, startDate))
            }
        }

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