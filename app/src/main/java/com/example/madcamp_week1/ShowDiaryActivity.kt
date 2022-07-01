package com.example.madcamp_week1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ShowDiaryActivity : AppCompatActivity() {
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_diary)

        textView = findViewById<TextView>(R.id.diaryTextView)

        val dateName = intent.getStringArrayExtra("date-name") as Array<String>

        val diaryText = applicationContext.assets.open("diaries/" + dateName[0] + "." + dateName[1] + ".txt")
                                                 .bufferedReader().use { it.readText() }

        println(diaryText)

        textView?.text = diaryText
    }
}