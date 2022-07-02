package com.example.madcamp_week1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView
import android.widget.TextView
import kotlin.math.max
import kotlin.math.min

class ShowPhotoDetailActivity : AppCompatActivity() {

    private var scaleGestureDetector: ScaleGestureDetector? = null
    private var scaleFactor = 1.0f

    var nameView: TextView?=null
    var imageView: ImageView?=null
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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector?.onTouchEvent(event)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_photo_detail)

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
        imageView?.setImageResource(choicePhotosList.get(pos).resId)
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