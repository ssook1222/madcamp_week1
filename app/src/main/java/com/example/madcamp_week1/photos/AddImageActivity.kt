package com.example.madcamp_week1.photos

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.madcamp_week1.R
import org.json.JSONArray
import org.json.JSONTokener
import java.io.File

class AddImageActivity : AppCompatActivity() {
    private var addImageButton : Button?=null
    private var personName : TextView ?= null
    private var resultLauncher: ActivityResultLauncher<Intent>? = null
    private var photosList = arrayListOf<ChoicePhotos>()
    private var addPhotosList = arrayListOf<AddPhotos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image)

        val tag = intent.getStringExtra("name")
        personName = findViewById(R.id.add_img_person)
        personName?.text = tag

        addImageButton= findViewById(R.id.button)
        addImageButton?.setOnClickListener(View.OnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)  {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                resultLauncher!!.launch(intent)
            }
            else {
                Toast.makeText(this,"갤러리 접근 권한을 허용해주세요.", Toast.LENGTH_LONG).show()
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    100
                )
            }
        })

        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val intent = result.data
                val callType = intent!!.getIntExtra("CallType", 0)
                if (callType == 0) {

                    val imageJsonFile = File(filesDir, "images.json")
                    if (!imageJsonFile.exists()) {
                        openFileOutput("images.json", Context.MODE_PRIVATE)
                    }

                    val imageJsonString: String = imageJsonFile.readText()

                    if (imageJsonString != "") {
                        val imageJsonArray = JSONTokener(imageJsonString).nextValue() as JSONArray
                        for (i in 0 until imageJsonArray.length()) {
                            val name = imageJsonArray.getJSONObject(i).getString("contactName")
                            val uriRaw = imageJsonArray.getJSONObject(i).getString("uri")
                            val uri: Uri = uriRaw.toUri()

                            photosList.add(ChoicePhotos(uri, name))
                            addPhotosList.add(AddPhotos(name, uri.toString()))
                        }
                    }

                    val uri = intent.data
                    photosList.add(ChoicePhotos(uri, tag))
                    addPhotosList.add(AddPhotos(tag,uri.toString())) //string으로 변환 후 json 객체로 만들어 파일에 추가하기 위함.

                    val gson = Gson()
                    val newImageListJson: String = gson.toJson(addPhotosList)
                    imageJsonFile.writeText(newImageListJson)

                    val intent = Intent(this@AddImageActivity, ShowPhotoActivity::class.java)
                    intent.putExtra("name",tag)
                    intent.putExtra("data",newImageListJson)
                    startActivity(intent)
                }
            }
        }
    }
}