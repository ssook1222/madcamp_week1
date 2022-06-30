package com.example.madcamp_week1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONTokener

class PhotoFragment : Fragment() {
    lateinit var recyclerView : RecyclerView
    var photosList = arrayListOf<Photos>()
    var thumbnailButton: ImageButton? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_photo, container, false)
        val photoAdapter = PhotoAdapter(requireContext(),photosList)

        photoAdapter.setOnItemClickListener(object:
        PhotoAdapter.OnItemClickListener{
            override fun onItemClick(view: View, photos: Photos, pos: Int) {
                val intent = Intent(activity, ChoicePhotoActivity::class.java)
                startActivity(intent)
            }
        }
        )
        thumbnailButton = rootView?.findViewById(R.id.photoContactImage)
        thumbnailButton?.setOnClickListener {
            println("들어왔어?")
            val intent = Intent(getActivity(), ChoicePhotoActivity::class.java)
            startActivity(intent)
        }

        if (photosList.isEmpty()) {
            val contactsJsonString: String = requireActivity().assets.open("contacts.json").bufferedReader().use {
                it.readText()
            }
            val photoJsonArray = JSONTokener(contactsJsonString).nextValue() as JSONArray

            for (i in 0 until photoJsonArray.length()) {
                val name = photoJsonArray.getJSONObject(i).getString("name")
                //sharedpreference가 default면
                photosList.add(Photos(name, R.drawable.sonagi_logo))
            }
        }

        recyclerView = rootView.findViewById(R.id.photoRecyclerView!!)as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = photoAdapter

        photoAdapter.notifyDataSetChanged()
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}