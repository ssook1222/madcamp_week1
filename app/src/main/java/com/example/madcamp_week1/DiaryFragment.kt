package com.example.madcamp_week1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONTokener

class DiaryFragment : Fragment() {
    lateinit var recyclerView : RecyclerView
    var diaryList = arrayListOf<Diary>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_diary, container, false)
        val diaryAdapter = DiaryAdapter(requireContext(), diaryList)

        diaryAdapter.setOnItemClickListener(object:
            DiaryAdapter.OnItemClickListener {
                override fun onTextClick(view: View, diary: Diary, pos: Int) {
                    val intent = Intent(activity, ShowDiaryActivity::class.java)
                    intent.putExtra("diaryData", arrayOf(diary.date, diary.name, diary.resId.toString(), diary.title))
                    startActivity(intent)
                }
            }
        )

        if (diaryList.isEmpty()) {
            val diaryJsonString: String = requireActivity().assets.open("diaries.json").bufferedReader().use {
                it.readText()
            }
            val diaryJsonArray = JSONTokener(diaryJsonString).nextValue() as JSONArray

            for (i in 0 until diaryJsonArray.length()) {
                val date = diaryJsonArray.getJSONObject(i).getString("date")
                val name = diaryJsonArray.getJSONObject(i).getString("name")
                val resId = diaryJsonArray.getJSONObject(i).getInt("resId")
                val title = diaryJsonArray.getJSONObject(i).getString("title")

                diaryList.add(Diary(date, name, R.drawable.sonagi_logo, title))
            }
        }

        recyclerView = rootView.findViewById(R.id.diaryRecyclerView!!)as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = diaryAdapter

        diaryAdapter.notifyDataSetChanged()

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}