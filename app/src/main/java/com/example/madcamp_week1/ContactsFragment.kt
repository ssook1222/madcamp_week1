package com.example.madcamp_week1

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONTokener
import java.io.File

class ContactsFragment : Fragment() {
    lateinit var recyclerView : RecyclerView
    var contactsList = arrayListOf<Contacts>()
    var addContact:Button ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_contacts, container, false)
        addContact = rootView?.findViewById(R.id.add_contacts)
        addContact?.setOnClickListener {
            val intent = Intent(activity, AddContacts::class.java)
            startActivity(intent)
        }

        val contactsJsonFile = File(context?.filesDir, "contacts.json")
        var contactsJsonString = ""

        if (contactsJsonFile.exists()) {
            contactsJsonString = contactsJsonFile.readText()
            println("I AM HERE! " + contactsJsonString)

            if (contactsList.size == 0 && contactsJsonString != "") {
                val contactsJsonArray = JSONTokener(contactsJsonString).nextValue() as JSONArray
                for (i in 0 until contactsJsonArray.length()) {
                    // Refer to Contacts.kt for correct member names!
                    val name = contactsJsonArray.getJSONObject(i).getString("contactName")
                    val phoneNumber = contactsJsonArray.getJSONObject(i).getString("phoneNumber")
                    val startDate = contactsJsonArray.getJSONObject(i).getString("startDate")
                    contactsList.add(Contacts(name, phoneNumber, startDate))
                }
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