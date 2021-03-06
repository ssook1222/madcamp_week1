package com.example.madcamp_week1.diaries

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week1.DataHandler
import com.example.madcamp_week1.MainActivity
import com.example.madcamp_week1.R
import com.example.madcamp_week1.contacts.Contacts
import com.google.gson.*
import org.json.JSONArray
import org.json.JSONTokener
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream


class DiaryAddActivity : AppCompatActivity() {
    lateinit var recyclerView : RecyclerView
    private var contactsList = arrayListOf<Contacts>()
    private var nameList = arrayListOf<String>()
    var newName: String = ""

    private var addDiaryPhotoButton: AppCompatButton? = null
    private var addDiaryButton: AppCompatButton? = null

    var uri: Uri? = null
    private var resultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_add)

        val contactsJsonFile = File(filesDir, "contacts.json")

        if (contactsJsonFile.exists()) {
            val contactsJsonString = contactsJsonFile.readText()

            if (contactsList.size == 0 && contactsJsonString != "") {
                val contactsJsonArray = JSONTokener(contactsJsonString).nextValue() as JSONArray
                for (i in 0 until contactsJsonArray.length()) {
                    // Refer to Contacts.kt for correct member names!
                    val name = contactsJsonArray.getJSONObject(i).getString("contactName")
                    nameList.add(name)
                }
            }
        }
        val nameAdapter = DiaryPersonAdapter(nameList)
        nameAdapter.setOnItemClickListener(object:
            DiaryPersonAdapter.OnItemClickListener {
            override fun onTextClick(view: View, name: String, pos: Int) {
                newName = view.findViewById<TextView>(R.id.addDiaryName).text.toString()
                //view.findViewById<CardView>(R.id.check_color).setBackgroundColor(Color.parseColor("#F3E8F4"))
                Toast.makeText(this@DiaryAddActivity, "${newName}?????? ?????????????????????.", Toast.LENGTH_SHORT).show()
            }
        }
        )

        val nameLayoutManager = LinearLayoutManager(this)
        nameLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        recyclerView = findViewById(R.id.addDiaryRecyclerView)
        recyclerView.layoutManager = nameLayoutManager

        recyclerView.adapter = nameAdapter

        addDiaryPhotoButton = findViewById(R.id.addDiaryPhoto)
        addDiaryPhotoButton?.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher!!.launch(intent)
        }

        addDiaryButton = findViewById(R.id.addDiary)
        addDiaryButton?.setOnClickListener{
            onClickAddDiaryButton(it)
        }

        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            result -> if (result.resultCode == RESULT_OK) {
                val intent = result.data
                val callType = intent!!.getIntExtra("CallType", 0)
                if (callType == 0) {
                    uri = intent.data

                    //?????? ????????? ????????? ???????????? ?????? ??????
                    try {
                        val ins:InputStream = contentResolver.openInputStream(uri!!)!!
                        val drawable = Drawable.createFromStream(ins, uri.toString())
                        addDiaryPhotoButton?.background = drawable
                    } catch(e: FileNotFoundException ){
                        Toast.makeText(this, "???????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun onClickAddDiaryButton(view: View) {
        val dh = DataHandler(applicationContext)
        val diaryList = dh.getDiariesList()

        val newYear = findViewById<EditText>(R.id.addDiaryYear).text.toString()
        val newMonth = findViewById<EditText>(R.id.addDiaryMonth).text.toString()
        val newDay = findViewById<EditText>(R.id.addDiaryDay).text.toString()

        //?????? ???????????? ???????????? ?????? ?????????
        //val newName = findViewById<EditText>(R.id.addDiaryName).text.toString()

        if (newName.isEmpty() || newName.isBlank()){
            Toast.makeText(applicationContext, "????????? ???????????????.", Toast.LENGTH_SHORT).show()
        }

        val newTitle = findViewById<EditText>(R.id.addDiaryTitle).text.toString()
        val newContent = findViewById<EditText>(R.id.addDiaryContent).text.toString()
        val newUri = this.uri

        if (this.uri == null) {
            Toast.makeText(applicationContext, "????????? ??????????????????.", Toast.LENGTH_SHORT).show()
            return
        }

        val newDiary = Diary("${newYear}-${newMonth}-${newDay}", newName, newUri, newTitle)
        diaryList.add(newDiary)

        val gson = GsonBuilder()
            .registerTypeAdapter(Diary::class.java, DiaryAdapter(applicationContext, diaryList))
            .create()

        val newDiaryListJson = gson.toJson(diaryList)
        dh.writeDiariesList(newDiaryListJson)

        // create a new txt file that contains diary content
        val newDiaryFile = File(filesDir, "$newTitle.txt")
        openFileOutput("$newTitle.txt", Context.MODE_PRIVATE)
        newDiaryFile.writeText(newContent)

        val intent = Intent(this@DiaryAddActivity, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@DiaryAddActivity, MainActivity::class.java)
        startActivity(intent)
    }
}