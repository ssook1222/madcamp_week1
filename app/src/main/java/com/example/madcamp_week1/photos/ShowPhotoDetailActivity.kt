package com.example.madcamp_week1.photos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import com.example.madcamp_week1.R
import org.json.JSONArray
import org.json.JSONTokener
import java.io.File
import kotlin.math.max
import kotlin.math.min

class ShowPhotoDetailActivity : AppCompatActivity() {

    private var scaleGestureDetector: ScaleGestureDetector? = null
    private var scaleFactor = 1.0f

    private var nameView: TextView?=null
    var imageView: ImageView?=null
    private var photosList = arrayListOf<ChoicePhotos>()
    private var choicePhotosList = arrayListOf<ChoicePhotos>()

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
                    val uriRaw = photosJsonArray.getJSONObject(i).getString("uri")
                    val uri = uriRaw.toUri()
                    photosList.add(ChoicePhotos(uri, name))
                }
            }
        }

        val name = intent.getStringExtra("name")
        nameView = findViewById(R.id.photo_with_name)
        nameView?.text = name

        for(i in 0 until photosList.size) {
            if (photosList[i].tag == name) { //이름이랑 같은 경우
                choicePhotosList.add(photosList[i]) // 추가
            }
        }

        val sharedPreferences = getSharedPreferences("photoDetail",0)
        val pos = sharedPreferences.getInt("new",0)

        imageView = findViewById(R.id.show_image)
        imageView?.setImageURI(choicePhotosList[pos].uri)
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