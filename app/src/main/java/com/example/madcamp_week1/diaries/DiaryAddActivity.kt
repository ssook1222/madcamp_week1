package com.example.madcamp_week1.diaries

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.net.toUri
import com.example.madcamp_week1.MainActivity
import com.example.madcamp_week1.R
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONTokener
import java.io.File

class DiaryAddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_add)
    }

    fun onClickAddDiaryButton(view: View) {
        var diaryList = arrayListOf<Diary>()

        val diaryJsonFile = File(filesDir, "diaries.json")
        if (!diaryJsonFile.exists()) {
            openFileOutput("diaries.json", Context.MODE_PRIVATE)
        }

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

        val newYear = findViewById<EditText>(R.id.addDiaryYear).text.toString()
        val newMonth = findViewById<EditText>(R.id.addDiaryMonth).text.toString()
        val newDay = findViewById<EditText>(R.id.addDiaryDay).text.toString()
        val newName = findViewById<EditText>(R.id.addDiaryName).text.toString()
        val newTitle = findViewById<EditText>(R.id.addDiaryTitle).text.toString()
        val newContent = findViewById<EditText>(R.id.addDiaryContent).text.toString()
        val newUri = Uri.EMPTY

        for (diary in diaryList) {
            if (diary.title == newTitle) {
                Toast.makeText(applicationContext, "중복된 제목입니다.", Toast.LENGTH_SHORT).show()
                return
            }
        }

        val newDiary = Diary("${newYear}-${newMonth}-${newDay}", newName, newUri, newTitle)
        diaryList.add(newDiary)

        val gson = Gson()
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