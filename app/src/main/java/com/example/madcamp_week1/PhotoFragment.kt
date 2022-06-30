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

class PhotoFragment : Fragment() {
    lateinit var recyclerView : RecyclerView
    var photosList = arrayListOf<Photos>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_photo, container, false)
        val contactAdapter = PhotoAdapter(photosList)

        if (photosList.isEmpty()) {
            val contactsJsonString: String = requireActivity().assets.open("contacts.json").bufferedReader().use {
                it.readText()
            }
            val photoJsonArray = JSONTokener(contactsJsonString).nextValue() as JSONArray

            for (i in 0 until photoJsonArray.length()) {
                val name = photoJsonArray.getJSONObject(i).getString("name")
                photosList.add(Photos(name, R.drawable.sonagi_logo))
            }
        }

        recyclerView = rootView.findViewById(R.id.photoRecyclerView!!)as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = contactAdapter

        contactAdapter.notifyDataSetChanged()
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}