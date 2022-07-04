package com.example.madcamp_week1.contacts

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week1.R
import com.example.madcamp_week1.DataHandler
import org.json.JSONArray
import org.json.JSONTokener
import java.io.File

class ContactsFragment : Fragment() {
    lateinit var recyclerView : RecyclerView
    var contactsList = arrayListOf<Contacts>()
    var addContact: Button? = null
    lateinit var contactsFragment: ContactsFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_contacts, container, false)

        addContact = rootView?.findViewById(R.id.add_contacts)
        addContact?.setOnClickListener {
            val intent = Intent(activity, ContactsAddActivity::class.java)
            startActivity(intent)
        }

        contactsFragment = this

        val dh = DataHandler(context)
        contactsList = dh.getContactsList()

        val contactAdapter = ContactAdapter(contactsList)
        contactAdapter.setOnItemClickListener(object:
            ContactAdapter.OnItemClickListener {
                override fun onCardViewClick(view: View, contacts: Contacts, pos: Int) {
                    val intent = Intent(activity, ContactsDetailActivity::class.java)
                    val name = view.findViewById<TextView>(R.id.contactName).text.toString()
                    val phone = view.findViewById<TextView>(R.id.phoneNumber).text.toString()
                    val date = view.findViewById<TextView>(R.id.startDate).text.toString()
                    intent.putStringArrayListExtra("contactDetailData", arrayListOf(name, phone, date))
                    activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.remove(contactsFragment)
                        ?.commit()
                    startActivity(intent)
                }
            }
        )

        recyclerView = rootView.findViewById(R.id.recyclerView)as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = contactAdapter

        contactAdapter.notifyDataSetChanged()
        return rootView
    }
}