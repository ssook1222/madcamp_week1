package com.example.madcamp_week1.contacts

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.madcamp_week1.MainActivity
import com.example.madcamp_week1.R
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONTokener
import java.io.File

class ContactsDetailActivity : AppCompatActivity() {
    private lateinit var detailName: TextView
    private lateinit var detailPhone: TextView
    private lateinit var detailDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_detail)

        // data is String[] { contactName, phoneNumber, startDate }
        val data = intent.getStringArrayListExtra("contactDetailData")
        detailName = findViewById<TextView>(R.id.detailName)
        detailPhone = findViewById<TextView>(R.id.detailPhone)
        detailDate = findViewById<TextView>(R.id.detailDate)

        detailName.text = data?.get(0)  // name
        detailPhone.text = data?.get(1) // phone
        detailDate.text = data?.get(2)  // date
    }

    fun onClickDeleteContactButton(view: View) {
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

        val contactName = findViewById<TextView>(R.id.detailName).text.toString()
        val filterdContactList = contactsList.filter { con -> con.contactName != contactName }

        val gson = Gson()
        val newContactsListJson: String = gson.toJson(filterdContactList)

        contactsJsonFile.writeText(newContactsListJson)

        Toast.makeText(applicationContext, "$contactName 삭제했습니다.", Toast.LENGTH_SHORT).show()

        val intent = Intent(this@ContactsDetailActivity, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@ContactsDetailActivity, MainActivity::class.java)
        startActivity(intent)
    }
}