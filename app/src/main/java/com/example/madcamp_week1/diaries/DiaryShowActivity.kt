package com.example.madcamp_week1.diaries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import com.example.madcamp_week1.MainActivity
import com.example.madcamp_week1.R
import java.io.File

class DiaryShowActivity : AppCompatActivity() {
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

        // diaryData is String[] { date, name, uri.toString(), title }
        val diaryData = intent.getStringArrayExtra("diaryData") as Array<String>

        val diaryFile = File(filesDir, "${diaryData[3]}.txt")
        val diaryText = diaryFile.readText()

        diaryTitleView.text = diaryData[3]
        diaryImageView.setImageURI(diaryData[2].toUri())
        diaryDateView.text = diaryData[0]
        diaryTextView.text = diaryText
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@DiaryShowActivity, MainActivity::class.java)
        startActivity(intent)
    }
}