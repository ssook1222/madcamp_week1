package com.example.madcamp_week1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChoicePhotoActivity : AppCompatActivity() {
    lateinit var recyclerView : RecyclerView
    var photosList = arrayListOf<ChoicePhotos>(
        ChoicePhotos(R.drawable.ssook1),
        ChoicePhotos(R.drawable.ssook2),
        ChoicePhotos(R.drawable.ssook3),
        ChoicePhotos(R.drawable.ssook4),
        ChoicePhotos(R.drawable.ssook5),
        ChoicePhotos(R.drawable.ssook6),
        ChoicePhotos(R.drawable.ssook7),
        ChoicePhotos(R.drawable.ssook8),
        ChoicePhotos(R.drawable.ssook9),
        ChoicePhotos(R.drawable.ssook10),
        ChoicePhotos(R.drawable.jy_lee1),
        ChoicePhotos(R.drawable.jy_lee2),
        ChoicePhotos(R.drawable.jy_lee3),
        ChoicePhotos(R.drawable.jy_lee4),
        ChoicePhotos(R.drawable.jy_lee5),
        ChoicePhotos(R.drawable.jy_lee6),
        ChoicePhotos(R.drawable.jy_lee7),
        ChoicePhotos(R.drawable.jy_lee8),
        ChoicePhotos(R.drawable.jy_lee9),
        ChoicePhotos(R.drawable.jy_lee10)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice_photo)

        recyclerView = findViewById(R.id.choiceRecyclerView) as RecyclerView
        val choicePhotoAdapter = ChoicePhotoAdapter(this, photosList)
        val layoutManager = GridLayoutManager(applicationContext,2)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = choicePhotoAdapter

    }
}