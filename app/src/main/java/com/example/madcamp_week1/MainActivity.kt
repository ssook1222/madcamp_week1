package com.example.madcamp_week1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madcamp_week1.contacts.ContactsFragment
import com.example.madcamp_week1.diaries.DiaryFragment
import com.example.madcamp_week1.photos.PhotoFragment
import com.google.android.material.navigation.NavigationBarView
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    
    public lateinit var mainActivity:MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {

        mainActivity=this

        var contactsFragment: ContactsFragment? = null
        var diaryFragment: DiaryFragment? = null
        var photoFragment: PhotoFragment? = null

        setTheme(R.style.SplashTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contactsFragment = ContactsFragment()
        diaryFragment = DiaryFragment()
        photoFragment = PhotoFragment()

        supportFragmentManager.beginTransaction().replace(R.id.container, contactsFragment!!).commit()

        var navigationBarView = findViewById<NavigationBarView>(R.id.menu)
        navigationBarView.itemIconTintList = null

        navigationBarView.setOnItemSelectedListener(
            NavigationBarView.OnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.contacts -> {
                        supportFragmentManager.beginTransaction().replace(R.id.container, contactsFragment!!)
                            .commit()
                        return@OnItemSelectedListener true
                    }

                    R.id.photos -> {
                        supportFragmentManager.beginTransaction().replace(R.id.container, photoFragment!!)
                            .commit()
                        return@OnItemSelectedListener true
                    }

                    R.id.diary -> {
                        supportFragmentManager.beginTransaction().replace(R.id.container, diaryFragment!!)
                            .commit()
                        return@OnItemSelectedListener true
                    }
                }
                false
            }
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}