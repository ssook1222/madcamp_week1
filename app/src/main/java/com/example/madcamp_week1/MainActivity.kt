package com.example.madcamp_week1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

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
}