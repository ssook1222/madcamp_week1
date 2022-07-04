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
        val diaryJsonFile = File(context!!.filesDir, "contacts.json")
        var contactsList = ArrayList<Contacts>()

        if (diaryJsonFile.exists()) {
            val contactsJsonString = diaryJsonFile.readText()

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
        val diaryJsonFile = File(context!!.filesDir, "contacts.json")
        if (!diaryJsonFile.exists()) {
            context.openFileOutput("contacts.json", Context.MODE_PRIVATE)
        }
        diaryJsonFile.writeText(newData)
    }

    fun getChoicePhotosList(): ArrayList<ChoicePhotos> {
        val imageJsonFile = File(context!!.filesDir, "images.json")
        val choicePhotosList = ArrayList<ChoicePhotos>()

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

    fun getDiariesList(): ArrayList<Diary> {
        val diaryJsonFile = File(context!!.filesDir, "diaries.json")
        val diaryList = ArrayList<Diary>()
        if (diaryJsonFile.exists()) {
            val diaryJsonString = diaryJsonFile.readText()
            if (diaryJsonString.isNotEmpty()) {
                val diaryJsonArray = JSONTokener(diaryJsonString).nextValue() as JSONArray
                for (i in 0 until diaryJsonArray.length()) {
                    val date = diaryJsonArray.getJSONObject(i).getString("date")
                    val name = diaryJsonArray.getJSONObject(i).getString("name")
                    val rawUri = diaryJsonArray.getJSONObject(i).getString("uri")
                    val title = diaryJsonArray.getJSONObject(i).getString("title")
                    diaryList.add(Diary(date, name, rawUri.toUri(), title))
                }
            }
        }
        return diaryList
    }

    fun writeDiariesList(newData: String) {
        val diaryJsonFile = File(context!!.filesDir, "diaries.json")
        if (!diaryJsonFile.exists()) {
            context.openFileOutput("diaries.json", Context.MODE_PRIVATE)
        }
        diaryJsonFile.writeText(newData)
    }
}