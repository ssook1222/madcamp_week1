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
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONTokener

class PhotoFragment : Fragment(){
    lateinit var recyclerView : RecyclerView
    var photosList = arrayListOf<Photos>()
    lateinit var photoFragment: PhotoFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_photo, container, false)
        val photoAdapter = PhotoAdapter(requireContext(),photosList)

        photoFragment = this

        photoAdapter.setOnItemClickListener(object:
        PhotoAdapter.OnItemClickListener{
            override fun onImageClick(view: View, photos: Photos, pos: Int) {
                val intent = Intent(activity, ChoicePhotoActivity::class.java)
                intent.putExtra("name",photos.contactName)
                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.remove(photoFragment)
                    ?.commit()
                startActivity(intent)
            }

           override fun onTextClick(view: View, photos: Photos, pos: Int) {
                val intent = Intent(activity, ShowPhotoActivity::class.java)
                intent.putExtra("name",photos.contactName)
                startActivity(intent)
            }
        }
        )
        val sharedPreference = (activity as MainActivity).getSharedPreferences("thumbnail",0)

        if (photosList.isEmpty()) {
            val contactsJsonString: String = requireActivity().assets.open("contacts.json").bufferedReader().use {
                it.readText()
            }
            val photoJsonArray = JSONTokener(contactsJsonString).nextValue() as JSONArray
            for (i in 0 until photoJsonArray.length()) {
                val name = photoJsonArray.getJSONObject(i).getString("name")
                val thumbnail_data = sharedPreference.getInt(name,0)

                if(thumbnail_data==0) {
                    photosList.add(Photos(name, R.drawable.sonagi_logo))
                }
                else{
                    photosList.add(Photos(name, thumbnail_data))
                }
            }
        }

        recyclerView = rootView.findViewById(R.id.photoRecyclerView!!)as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = photoAdapter

        photoAdapter.notifyDataSetChanged()
        return rootView
    }
}