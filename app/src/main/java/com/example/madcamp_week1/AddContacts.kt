package com.example.madcamp_week1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONTokener
import java.io.File

class AddContacts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contacts)
    }

    fun onClickAddContactButton(view: View) {
        var contactsList = arrayListOf<Contacts>()

        val contactsJsonFile = File(filesDir, "contacts.json")
        if (!contactsJsonFile.exists()) {
            openFileOutput("contacts.json", Context.MODE_PRIVATE)
        }

        val contactsJsonString: String = contactsJsonFile.readText()

        if (contactsJsonString != "") {
            val contactsJsonArray = JSONTokener(contactsJsonString).nextValue() as JSONArray
            for (i in 0 until contactsJsonArray.length()) {
                // Refer to Contacts.kt for correct member names!
                val name = contactsJsonArray.getJSONObject(i).getString("contactName")
                val phoneNumber = contactsJsonArray.getJSONObject(i).getString("phoneNumber")
                val startDate = contactsJsonArray.getJSONObject(i).getString("startDate")
                contactsList.add(Contacts(name, phoneNumber, startDate))
            }
        }

        val newContactPhone = findViewById<EditText>(R.id.add_phone).text.toString()
        val newContactName = findViewById<EditText>(R.id.add_name).text.toString()
        val newContactYear = findViewById<EditText>(R.id.add_year).text.toString()
        val newContactMonth = findViewById<EditText>(R.id.add_month).text.toString()
        val newContactDay = findViewById<EditText>(R.id.add_day).text.toString()

        val newContact = Contacts(newContactName, newContactPhone, "${newContactYear}-${newContactMonth}-${newContactDay}")
        contactsList.add(newContact)

        val gson = Gson()
        val newContactsListJson: String = gson.toJson(contactsList)

        contactsJsonFile.writeText(newContactsListJson)

        val intent = Intent(this@AddContacts, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@AddContacts, MainActivity::class.java)
        startActivity(intent)
    }
}