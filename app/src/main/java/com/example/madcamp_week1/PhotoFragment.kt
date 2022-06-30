package com.example.madcamp_week1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PhotoFragment : Fragment() {
    lateinit var recyclerView : RecyclerView

    var photosList = arrayListOf<Photos>(
        Photos("ssook",R.drawable.box)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_photo, container, false)
        val contactAdapter = PhotoAdapter(photosList)

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