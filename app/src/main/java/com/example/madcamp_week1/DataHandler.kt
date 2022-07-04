package com.example.madcamp_week1

import android.content.Context
import androidx.core.net.toUri
import com.example.madcamp_week1.contacts.Contacts
import com.example.madcamp_week1.diaries.Diary
import com.example.madcamp_week1.photos.ChoicePhotos
import org.json.JSONArray
import org.json.JSONTokener
import java.io.File

class DataHandler(val context: Context?) {
    fun getContactsList(): ArrayList<Contacts> {
        val contactsJsonFile = File(context!!.filesDir, "contacts.json")
        var contactsList = ArrayList<Contacts>()

        if (contactsJsonFile.exists()) {
            val contactsJsonString = contactsJsonFile.readText()

            if (contactsJsonString.isNotEmpty()) {
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
        return contactsList
    }

    fun writeContactsList(newData: String) {
        val contactsJsonFile = File(context!!.filesDir, "contacts.json")
        if (!contactsJsonFile.exists()) {
            context.openFileOutput("contacts.json", Context.MODE_PRIVATE)
        }
        contactsJsonFile.writeText(newData)
    }

    fun getChoicePhotosList(): ArrayList<ChoicePhotos> {
        val imageJsonFile = File(context!!.filesDir, "images.json")
        var choicePhotosList = ArrayList<ChoicePhotos>()

        if (imageJsonFile.exists()) {
            val imageJsonString = imageJsonFile.readText()
            if (imageJsonString.isNotEmpty()) {
                val imageJsonArray = JSONTokener(imageJsonString).nextValue() as JSONArray
                for (i in 0 until imageJsonArray.length()) {
                    val name = imageJsonArray.getJSONObject(i).getString("contactName")
                    val uri = imageJsonArray.getJSONObject(i).getString("uri").toUri()
                    choicePhotosList.add(ChoicePhotos(uri, name))
                }
            }
        }

        return choicePhotosList
    }

    fun getDiariesList(): ArrayList<Diary>? {
        return null
    }
}