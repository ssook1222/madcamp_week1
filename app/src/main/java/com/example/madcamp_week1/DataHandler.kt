package com.example.madcamp_week1

import android.app.Application
import android.content.Context
import com.example.madcamp_week1.contacts.Contacts
import com.example.madcamp_week1.diaries.Diary
import com.example.madcamp_week1.photos.Photos
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

    fun getPhotosList(): ArrayList<Photos>? {
        val imageJsonFile = File(context!!.filesDir, "images.json")


        return null
    }

    fun getDiariesList(): ArrayList<Diary>? {
        return null
    }
}