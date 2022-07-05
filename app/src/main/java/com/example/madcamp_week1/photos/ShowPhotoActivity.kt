package com.example.madcamp_week1.photos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week1.DataHandler
import com.example.madcamp_week1.MainActivity
import com.example.madcamp_week1.R

class ShowPhotoActivity : AppCompatActivity() {
    lateinit var recyclerView : RecyclerView
    private var choicePhotosList = arrayListOf<ChoicePhotos>()
    private var personName: TextView?=null
    private var addButton : Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_photo)

        val dh = DataHandler(applicationContext)
        choicePhotosList = dh.getChoicePhotosList()

        recyclerView = findViewById(R.id.showRecyclerView)

        val name = intent.getStringExtra("name")
        personName = findViewById(R.id.personName)
        personName?.text = name

        addButton = findViewById(R.id.add_image)
        addButton?.setOnClickListener {
            val intent = Intent(this@ShowPhotoActivity, AddImageActivity::class.java)
            intent.putExtra("name",name)
            startActivity(intent)
        }

        choicePhotosList = choicePhotosList.filter { photo -> photo.tag == name } as ArrayList<ChoicePhotos>

        val choicePhotoAdapter = ChoicePhotoAdapter(this, choicePhotosList)
        val layoutManager = GridLayoutManager(applicationContext,2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = choicePhotoAdapter

//        이미지 나오면 업데이트
        choicePhotoAdapter.setOnItemClickListener(object : ChoicePhotoAdapter.OnItemClickListener {
            override fun onItemClick(view: View, choicePhotos: ChoicePhotos, pos: Int) {
                val intent = Intent(this@ShowPhotoActivity, ShowPhotoDetailActivity::class.java)
                intent.putExtra("name",name)
                val sharedPreferences = getSharedPreferences("photoDetail",0)
                val editor = sharedPreferences.edit()
                editor.putInt("new",pos)
                editor.apply()
                startActivity(intent)
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@ShowPhotoActivity, MainActivity::class.java)
        finishAffinity()
        startActivity(intent)
    }
}