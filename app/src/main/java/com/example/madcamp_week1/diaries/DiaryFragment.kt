package com.example.madcamp_week1.diaries

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week1.DataHandler
import com.example.madcamp_week1.R

class DiaryFragment : Fragment() {
    lateinit var recyclerView : RecyclerView
    private var diaryList = arrayListOf<Diary>()
    lateinit var diaryFragment: DiaryFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_diary, container, false)
        diaryFragment = this

        val dh = DataHandler(context)
        diaryList = dh.getDiariesList()

        val addDiary = rootView?.findViewById<Button>(R.id.add_diary)
        addDiary?.setOnClickListener {
            val intent = Intent(activity, DiaryAddActivity::class.java)
            startActivity(intent)
        }

        val diaryAdapter = DiaryAdapter(requireContext(), diaryList)
        diaryAdapter.setOnItemClickListener(object:
            DiaryAdapter.OnItemClickListener {
                override fun onTextClick(view: View, diary: Diary, pos: Int) {
                    val intent = Intent(activity, DiaryShowActivity::class.java)
                    intent.putExtra("diaryData", arrayOf(diary.date, diary.name, diary.uri.toString(), diary.title))
                    activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.remove(diaryFragment)
                        ?.commit()
                    startActivity(intent)
                }
            }
        )

        recyclerView = rootView.findViewById(R.id.diaryRecyclerView)as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = diaryAdapter

        diaryAdapter.notifyDataSetChanged()

        return rootView
    }

}