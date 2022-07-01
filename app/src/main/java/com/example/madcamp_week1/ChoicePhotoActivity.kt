package com.example.madcamp_week1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max

class ChoicePhotoActivity : AppCompatActivity() {
    lateinit var recyclerView : RecyclerView
    var photosList = arrayListOf<ChoicePhotos>(
        ChoicePhotos(R.drawable.ssook1,"chitos"),
        ChoicePhotos(R.drawable.ssook2,"ssook"),
        ChoicePhotos(R.drawable.ssook3,"chitos"),
        ChoicePhotos(R.drawable.ssook4,"ssook"),
        ChoicePhotos(R.drawable.ssook5,"ssook"),
        ChoicePhotos(R.drawable.ssook6,"ssook"),
        ChoicePhotos(R.drawable.ssook7,"ssook"),
        ChoicePhotos(R.drawable.ssook8,"ssook"),
        ChoicePhotos(R.drawable.ssook9,"ssook"),
        ChoicePhotos(R.drawable.ssook10,"chitos"),
        ChoicePhotos(R.drawable.jy_lee1,"jylee"),
        ChoicePhotos(R.drawable.jy_lee2,"jylee"),
        ChoicePhotos(R.drawable.jy_lee3,"jylee"),
        ChoicePhotos(R.drawable.jy_lee4,"chitos"),
        ChoicePhotos(R.drawable.jy_lee5,"jylee"),
        ChoicePhotos(R.drawable.jy_lee6,"chitos"),
        ChoicePhotos(R.drawable.jy_lee7,"jylee"),
        ChoicePhotos(R.drawable.jy_lee8,"jylee"),
        ChoicePhotos(R.drawable.jy_lee9,"jylee"),
        ChoicePhotos(R.drawable.jy_lee10,"jylee")
    )
    var choicePhotosList = arrayListOf<ChoicePhotos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice_photo)

        recyclerView = findViewById(R.id.choiceRecyclerView) as RecyclerView
        val choicePhotoAdapter = ChoicePhotoAdapter(this, choicePhotosList)
        val layoutManager = GridLayoutManager(applicationContext,2)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = choicePhotoAdapter

        val name = intent.getStringExtra("name")
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
                editor.putInt("${choicePhotosList.get(pos).tag}",choicePhotosList.get(pos).resId)
                editor.apply()
                println(choicePhotosList.get(pos).resId)
            }
        }
        )
    }
}