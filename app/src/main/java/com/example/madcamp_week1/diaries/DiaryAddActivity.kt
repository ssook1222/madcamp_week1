package com.example.madcamp_week1.diaries

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.net.toUri
import com.example.madcamp_week1.MainActivity
import com.example.madcamp_week1.R
import com.google.gson.*
import org.json.JSONArray
import org.json.JSONTokener
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream


class DiaryAddActivity : AppCompatActivity() {
    var addDiaryPhotoButton: AppCompatButton? = null
    var addDiaryButton: AppCompatButton? = null
    var uri: Uri? = null
    private var resultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_add)

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

                    //버튼 누르면 추가한 이미지로 바로 변경
                    try{
                        var ins:InputStream=contentResolver.openInputStream(uri!!)!!
                        var drawable = Drawable.createFromStream(ins, uri.toString())
                        addDiaryPhotoButton?.setBackground(drawable)
                    } catch(e: FileNotFoundException ){
                        Toast.makeText(this, "이미지가 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun onClickAddDiaryButton(view: View) {
        var diaryList = arrayListOf<Diary>()

        val diaryJsonFile = File(filesDir, "diaries.json")
        if (!diaryJsonFile.exists()) {
            openFileOutput("diaries.json", Context.MODE_PRIVATE)
        }

        val diaryJsonString = diaryJsonFile.readText()

        // construct a json that contains information about
        // already existing diaries
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

        val newYear = findViewById<EditText>(R.id.addDiaryYear).text.toString()
        val newMonth = findViewById<EditText>(R.id.addDiaryMonth).text.toString()
        val newDay = findViewById<EditText>(R.id.addDiaryDay).text.toString()
        val newName = findViewById<EditText>(R.id.addDiaryName).text.toString()
        val newTitle = findViewById<EditText>(R.id.addDiaryTitle).text.toString()
        val newContent = findViewById<EditText>(R.id.addDiaryContent).text.toString()
        val newUri = this.uri

        if (this.uri == null) {
            Toast.makeText(applicationContext, "사진을 선택해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

//        for (diary in diaryList) {
//            if (diary.title == newTitle) {
//                Toast.makeText(applicationContext, "중복된 제목입니다.", Toast.LENGTH_SHORT).show()
//                return
//            }
//        }

        val newDiary = Diary("${newYear}-${newMonth}-${newDay}", newName, newUri, newTitle)
        diaryList.add(newDiary)

        val gson = GsonBuilder()
            .registerTypeAdapter(Diary::class.java, DiaryAdapter(applicationContext, diaryList))
            .create()
        val newDiaryListJson = gson.toJson(diaryList)
        diaryJsonFile.writeText(newDiaryListJson)

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