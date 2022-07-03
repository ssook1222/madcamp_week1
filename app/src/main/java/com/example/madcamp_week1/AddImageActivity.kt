package com.example.madcamp_week1

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import org.json.JSONArray
import org.json.JSONTokener
import java.io.File

class AddImageActivity : AppCompatActivity() {
    var addImageButton : Button?=null
    private var resultLauncher: ActivityResultLauncher<Intent>? = null
    var photosList = arrayListOf<ChoicePhotos>()
    var addPhotosList = arrayListOf<AddPhotos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image)

        val tag = intent.getStringExtra("name")

        addImageButton= findViewById(R.id.button)
        addImageButton?.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher!!.launch(intent);
        })

        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val intent = result.data
                val CallType = intent!!.getIntExtra("CallType", 0)
                if (CallType == 0) {

                    val imageJsonFile = File(filesDir, "images.json")
                    if (!imageJsonFile.exists()) {
                        openFileOutput("images.json", Context.MODE_PRIVATE)
                    }

                    val imageJsonString: String = imageJsonFile.readText()

                    if (imageJsonString != "") {
                        val imageJsonArray = JSONTokener(imageJsonString).nextValue() as JSONArray
                        for (i in 0 until imageJsonArray.length()) {
                            val name = imageJsonArray.getJSONObject(i).getString("contactName")
                            val uri_raw = imageJsonArray.getJSONObject(i).getString("uri")
                            val uri: Uri? = uri_raw.toUri()

                            photosList.add(ChoicePhotos(uri, name))
                            addPhotosList.add(AddPhotos(name,uri.toString()))
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