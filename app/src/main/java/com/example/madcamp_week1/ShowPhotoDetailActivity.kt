package com.example.madcamp_week1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import org.json.JSONArray
import org.json.JSONTokener
import java.io.File
import kotlin.math.max
import kotlin.math.min

class ShowPhotoDetailActivity : AppCompatActivity() {

    private var scaleGestureDetector: ScaleGestureDetector? = null
    private var scaleFactor = 1.0f

    var nameView: TextView?=null
    var imageView: ImageView?=null
    var photosList = arrayListOf<ChoicePhotos>()
    var choicePhotosList = arrayListOf<ChoicePhotos>()

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector?.onTouchEvent(event)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_photo_detail)

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

        val name = intent.getStringExtra("name")
        nameView = findViewById(R.id.photo_with_name)
        nameView?.text = name

        for(i in 0 until photosList.size) {
            if (photosList.get(i).tag == name) { //이름이랑 같은 경우
                choicePhotosList.add(photosList.get(i)) // 추가
            }
        }

        val sharedPreferences = getSharedPreferences("photoDetail",0)
        val pos = sharedPreferences.getInt("new",0)

        imageView = findViewById(R.id.show_image)
        imageView?.setImageURI(choicePhotosList.get(pos).uri)
        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())
    }

    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            scaleFactor *= scaleGestureDetector.scaleFactor

            // 최소 0.5, 최대 2배
            scaleFactor = max(0.5f, min(scaleFactor, 2.0f))

            imageView?.scaleX = scaleFactor
            imageView?.scaleY = scaleFactor
            return true
        }
    }
}