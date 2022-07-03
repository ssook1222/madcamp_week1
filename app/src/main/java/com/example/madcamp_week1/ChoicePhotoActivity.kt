package com.example.madcamp_week1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max

class ChoicePhotoActivity : AppCompatActivity() {
    lateinit var recyclerView : RecyclerView
    var photosList = arrayListOf<ChoicePhotos>()
    var choicePhotosList = arrayListOf<ChoicePhotos>()
    var personName:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice_photo)

        recyclerView = findViewById(R.id.choiceRecyclerView) as RecyclerView
        val choicePhotoAdapter = ChoicePhotoAdapter(this, choicePhotosList)
        val layoutManager = GridLayoutManager(applicationContext,2)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = choicePhotoAdapter

        val name = intent.getStringExtra("name")
        personName = findViewById(R.id.personName)
        personName?.text = name

        for(i in 0 until photosList.size) {
            if (photosList.get(i).tag == name) { //이름이랑 같은 경우
                choicePhotosList.add(photosList.get(i)) // 추가
                choicePhotoAdapter.notifyDataSetChanged()
            }
        }

        choicePhotoAdapter.setOnItemClickListener(object : ChoicePhotoAdapter.OnItemClickListener{
            override fun onItemClick(view: View, choicePhotos: ChoicePhotos, pos: Int) {
                val sharedPreferences = getSharedPreferences("thumbnail",0)
                val editor = sharedPreferences.edit()
                editor.putString("${choicePhotosList.get(pos).tag}",choicePhotosList.get(pos).uri.toString()) //string으로 넣었으니 uri로 파싱해야 함.
                editor.apply()

                Toast.makeText(applicationContext, "대표 기억 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@ChoicePhotoActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@ChoicePhotoActivity, MainActivity::class.java)
        startActivity(intent)
    }
}