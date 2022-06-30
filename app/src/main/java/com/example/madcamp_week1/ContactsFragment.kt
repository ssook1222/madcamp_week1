package com.example.madcamp_week1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madcamp_week1.databinding.FragmentContactsBinding

class ContactsFragment : Fragment() {

    private var contactsBinding: FragmentContactsBinding?= null
    private val binding get() = contactsBinding!!

    var contactsList = arrayListOf<Contacts>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contactsBinding = FragmentContactsBinding.inflate(inflater, container, false)
        val contactAdapter = ContactAdapter(this, contactsList)

        contactsList.add(Contacts("ssook","010-1111-1111","06/29"))

        contactsBinding!!.recycelerView.layoutManager=LinearLayoutManager(activity)
        contactsBinding!!.recycelerView.adapter=contactAdapter

        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        contactsBinding = null
    }
}