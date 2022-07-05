package com.example.madcamp_week1.contacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.madcamp_week1.DataHandler
import com.example.madcamp_week1.MainActivity
import com.example.madcamp_week1.R
import com.google.gson.Gson

class ContactsDetailActivity : AppCompatActivity() {
    private lateinit var detailName2: TextView
    private lateinit var detailName3: TextView
    private lateinit var detailName4: TextView
    private lateinit var detailPhone: TextView
    private lateinit var detailDate: TextView
    private lateinit var finishButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_detail)

        // data is String[] { contactName, phoneNumber, startDate }
        val data = intent.getStringArrayListExtra("contactDetailData")
        detailName2 = findViewById(R.id.detailName2)
        detailName3 = findViewById(R.id.detailName3)
        detailName4 = findViewById(R.id.detailName4)
        detailPhone = findViewById(R.id.detailPhone)
        detailDate = findViewById(R.id.detailDate)

        detailName2.text = data?.get(0)  // name
        detailName3.text = data?.get(0)  // name
        detailName4.text = data?.get(0)  // name
        detailPhone.text = data?.get(1) // phone
        detailDate.text = data?.get(2)  // date

        finishButton = findViewById(R.id.end_point)

        finishButton.setOnClickListener {
            onClickDeleteContactButton(it)
        }
    }

    private fun onClickDeleteContactButton(view: View) {
        val dh = DataHandler(applicationContext)
        val contactsList = dh.getContactsList()

        val contactName = findViewById<TextView>(R.id.detailName2).text.toString()
        val filteredContactList = contactsList.filter { con -> con.contactName != contactName }

        val gson = Gson()
        val newContactsListJson: String = gson.toJson(filteredContactList)

        dh.writeContactsList(newContactsListJson)

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