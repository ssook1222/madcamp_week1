package com.example.madcamp_week1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ShowDiaryActivity : AppCompatActivity() {
    private lateinit var diaryTitleView: TextView
    private lateinit var diaryTextView: TextView
    private lateinit var diaryImageView: ImageView
    private lateinit var diaryDateView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_diary)

        diaryTitleView = findViewById<TextView>(R.id.diaryTitleView)
        diaryTextView = findViewById<TextView>(R.id.diaryTextView)
        diaryImageView = findViewById<ImageView>(R.id.diaryImageView)
        diaryDateView = findViewById<TextView>(R.id.diaryDateView)

        // diaryData is String[] { date, name, resId.toString(), title }
        val diaryData = intent.getStringArrayExtra("diaryData") as Array<String>

        val diaryText = applicationContext.assets.open("diaries/" + diaryData[0] + "." + diaryData[1] + ".txt")
            .bufferedReader().use { it.readText() }

        diaryTitleView?.text = diaryData[3]
        diaryImageView?.setImageResource(diaryData[2].toInt())
        diaryDateView?.text = diaryData[0]
        diaryTextView?.text = diaryText
    }
}