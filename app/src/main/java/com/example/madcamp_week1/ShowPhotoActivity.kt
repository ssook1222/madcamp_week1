package com.example.madcamp_week1

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week1.contacts.Contacts
import org.json.JSONArray
import org.json.JSONTokener
import java.io.File

class ShowPhotoActivity : AppCompatActivity() {
    lateinit var recyclerView : RecyclerView
    var photosList = arrayListOf<ChoicePhotos>()
    var choicePhotosList = arrayListOf<ChoicePhotos>()
    var personName: TextView?=null
    var addButton : Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_photo)

        val imageJsonFile = File(filesDir, "images.json")
        var imageJsonString = ""

        if (imageJsonFile.exists()) {
            imageJsonString = imageJsonFile.readText()

            if (photosList.size == 0 && imageJsonString != "") {
                val photosJsonArray = JSONTokener(imageJsonString).nextValue() as JSONArray
                for (i in 0 until photosJsonArray.length()) {
                    val name = photosJsonArray.getJSONObject(i).getString("contactName")
                    val uri_raw = photosJsonArray.getJSONObject(i).getString("uri")
                    val uri = uri_raw.toUri()
                    photosList.add(ChoicePhotos(uri, name))
                }
            }
        }

        recyclerView = findViewById(R.id.showRecyclerView) as RecyclerView

        val name = intent.getStringExtra("name")
        personName = findViewById(R.id.personName)
        personName?.text = name

        addButton = findViewById(R.id.add_image)
        addButton?.setOnClickListener {
            val intent = Intent(this@ShowPhotoActivity, AddImageActivity::class.java)
            intent.putExtra("name",name)
            startActivity(intent)
        }
//        이미지 나오면 업데이트
        for(i in 0 until photosList.size) {
            if (photosList.get(i).tag == name) { //이름이랑 같은 경우
                choicePhotosList.add(photosList.get(i)) // 추가
            }
        }
        val choicePhotoAdapter = ChoicePhotoAdapter(this, choicePhotosList)
        val layoutManager = GridLayoutManager(applicationContext,2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = choicePhotoAdapter

//        이미지 나오면 업데이트
        choicePhotoAdapter.setOnItemClickListener(object : ChoicePhotoAdapter.OnItemClickListener{
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