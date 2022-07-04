package com.example.madcamp_week1.photos

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week1.DataHandler
import com.example.madcamp_week1.MainActivity
import com.example.madcamp_week1.R
import org.json.JSONArray
import org.json.JSONTokener
import java.io.File


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

        val resources = requireContext().resources

        photoAdapter.setOnItemClickListener(object:
            PhotoAdapter.OnItemClickListener {
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
            val dh = DataHandler(context)
            val contactsList = dh.getContactsList()

            val contactJsonFile = File(context?.filesDir, "contacts.json")

            if (contactJsonFile.exists()) {
                if (photosList.size == 0 && contactsList.isNotEmpty()) {
                    for (contact in contactsList) {
                        val name = contact.contactName
                        val thumbnailData = sharedPreference.getString(name, "")
                        if (thumbnailData!!.isEmpty()) {
                            photosList.add(
                                Photos(name, Uri.parse(
                                    ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                                    resources.getResourcePackageName(R.drawable.sonagi_logo) + '/' +
                                    resources.getResourceTypeName(R.drawable.sonagi_logo) + '/' +
                                    resources.getResourceEntryName(R.drawable.sonagi_logo))
                                )
                            )
                        }
                        else {
                            photosList.add(Photos(name, thumbnailData.toUri()))
                        }
                    }
                }
            }
        }

        recyclerView = rootView.findViewById(R.id.photoRecyclerView)as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = photoAdapter

        photoAdapter.notifyDataSetChanged()
        return rootView
    }
}